package fr.univtln.bruno.demos.jaxrs;

/*-
 * #%L
 * JAXRS Utils
 * %%
 * Copyright (C) 2020 - 2021 Université de Toulon
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */


import fr.univtln.bruno.demos.jpa.dao.DAO;
import fr.univtln.bruno.demos.jpa.dao.Page;
import fr.univtln.bruno.demos.jpa.dao.exceptions.DAOException;
import fr.univtln.bruno.demos.jpa.entities.SimpleEntity;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bruno on 03/12/14.
 */

@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public abstract class AbstractDAOResource<T extends SimpleEntity> implements DAOResource<T> {
    private int maxpageSize = 10;
    private DAO<T> dao;

    protected AbstractDAOResource(DAO<T> dao) {
        this.dao = dao;
    }

    @Override
    @GET
    @Path("{id}")
    public T find(@PathParam("id") long id) throws DAOException {
        return dao.find(id);
    }

    @Override
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) throws DAOException {
        dao.delete(id);
    }

    public long size() throws DAOException {
        return dao.getSize();
    }

    @Override
    @HEAD
    public Response metadata() throws DAOException {
        return Response.status(Response.Status.OK)
                .header("X-Total-Item-Count", size())
                .header("Access-Control-Expose-Headers",
                        "X-Total-Item-Count, X-Total-Page-Count")
                .build();
    }

    @Override
    @GET
    public Response findAll(@QueryParam("reverse") @DefaultValue("false") boolean reverse,
                            @QueryParam("pagenumber") @DefaultValue("0") int pageNumber,
                            @QueryParam("perpage") @DefaultValue("-1") int perPage,
                            @QueryParam("limit") @DefaultValue("-1") int limit,
                            @Context UriInfo uriInfo) throws DAOException {
        Response response;
        if (pageNumber == -1)
            response = listToResponse(dao.findAll(reverse, limit), uriInfo)
                    .build();
        else {
            if (perPage < 0) perPage = getMaxPageSize();
            Page result = dao.findAllByPage(reverse, pageNumber, perPage, limit);
            response = pageToResponse(result, uriInfo)
                    .build();
        }
        return response;
    }

    protected Response.ResponseBuilder listToResponse(List list, UriInfo uriInfo) throws DAOException {
        return Response.status(Response.Status.OK).entity(list)
                .header("X-Total-Item-Count", list.size())
                .header("Access-Control-Expose-Headers",
                        "X-Total-Item-Count, X-Total-Page-Count");
    }

    protected Response.ResponseBuilder pageToResponse(Page page, UriInfo uriInfo) throws DAOException {
        List<String> links = new ArrayList<>();
        if (page.PAGE_NUMBER > 0) {
            links.add("<" + uriInfo.getAbsolutePath() + "?pagenumber=0" + "&perpage=" + page.PAGE_SIZE + ">; rel=\"first\"");
            links.add("<" + uriInfo.getAbsolutePath() + "?pagenumber=" + (page.PAGE_NUMBER + 1) + "&perpage=" + page.PAGE_SIZE + ">; rel=\"previous\"");
        }
        if (page.PAGE_NUMBER < page.TOTAL_PAGES - 1) {
            links.add("<" + uriInfo.getAbsolutePath() + "?pagenumber=" + (page.PAGE_NUMBER + 1) + "&perpage=" + page.PAGE_SIZE + ">; rel=\"next\"");
            links.add("<" + uriInfo.getAbsolutePath() + "?pagenumber=" + (page.TOTAL_PAGES - 1) + "&perpage=" + page.PAGE_SIZE + ">; rel=\"last\"");
        }

        return Response.status(Response.Status.OK).entity(page.content)
                .header("Link", links.stream().collect(Collectors.joining(", ")))
                .header("X-Total-Item-Count", page.TOTAL_ITEMS)
                .header("X-Total-Page-Count", page.TOTAL_PAGES)
                .header("Access-Control-Expose-Headers", "X-Total-Item-Count, X-Total-Page-Count");
    }

    @Override
    @GET
    @Path("ids")
    public List<Long> getIds(@QueryParam("reverse") @DefaultValue("false") boolean reverse) throws DAOException {
        return dao.getIds(reverse);
    }

    @Override
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public int create(List<T> list) throws DAOException {
        return dao.persist(list);
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public T update(T t) throws DAOException {
        return dao.update(t);
    }

    @Override
    @DELETE
    public int delete() throws DAOException {
        return dao.deleteAll();
    }

    @Override
    @GET
    @Path("maxpagesize")
    public int getMaxPageSize() {
        return maxpageSize;
    }

    @Override
    @PUT
    @Path("maxpagesize")
    //@RolesAllowed("admin")
    public void setMaxPageSize(int pageSize) {
        this.maxpageSize = pageSize;
    }
}

