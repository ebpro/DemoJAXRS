package fr.univtln.bruno.demos.jaxrs.resources;

/*-
 * #%L
 * JAXRS Server
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

import fr.univtln.bruno.demos.jaxrs.model.NumberListDAO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.java.Log;

import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Root resource (exposed at "message" path)
 */
@Log
@Path("randomnumbers")
@Produces(MediaType.TEXT_PLAIN)
public class RandomNumbersResource {
    /**
     * The Number list dao.
     */
    protected final NumberListDAO numberListDAO = NumberListDAO.of();
    private final SecureRandom random = new SecureRandom();

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    public String get() {
        return Arrays.toString(numberListDAO.get());
    }

    /**
     * Append string.
     *
     * @return the string
     */
    @POST
    public String append() {
        final int newInt = random.nextInt(6) + 1;
        numberListDAO.add(newInt);
        return String.valueOf(newInt);
    }

    /**
     * Start string.
     *
     * @return the string
     */
    @PUT
    public String start() {
        numberListDAO.clear();
        return append();
    }

    /**
     * Delete.
     */
    @DELETE
    public void delete() {
        numberListDAO.clear();
    }

    /**
     * Gets metadata.
     *
     * @return the metadata
     */
    @HEAD
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMetadata() {
        return Response.status(Response.Status.OK)
                .header("last_update", numberListDAO.getChangeTime())
                .build();
    }

}
