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

import fr.univtln.bruno.demos.jpa.dao.exceptions.DAOException;
import fr.univtln.bruno.demos.jpa.entities.SimpleEntity;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;

/**
 * Created by bruno on 05/12/14.
 */
public interface DAOResource<T extends SimpleEntity> {
    T find(long id) throws DAOException;

    void delete(Long id) throws DAOException;

    Response metadata() throws DAOException;

    Response findAll(boolean reverse, int pageNumber, int perPage, int limit, UriInfo uriInfo) throws DAOException;

    List<Long> getIds(boolean reverse) throws DAOException;

    int create(List<T> list) throws DAOException;

    T update(@Valid T t) throws DAOException;

    int delete() throws DAOException;

    public int getMaxPageSize();

    public void setMaxPageSize(int pageSize);
}
