package fr.univtln.bruno.demos.jaxrs.exceptions.mappers;

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


import fr.univtln.bruno.demos.jaxrs.exceptions.ErrorMessage;
import fr.univtln.bruno.utils.AppConstants;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

    public Response toResponse(Exception ex) {

        ErrorMessage errorMessage = new ErrorMessage();
        setHttpStatus(ex, errorMessage);
        errorMessage.setCode(AppConstants.ErrorCode.GENERIC_EXCEPTION);
        errorMessage.setMessage(ex.getMessage());

        return Response.status(errorMessage.getStatus())
                .entity(errorMessage)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private void setHttpStatus(Throwable ex, ErrorMessage errorMessage) {
        if (ex instanceof WebApplicationException) { //NICE way to combine both of methods, say it in the blog
            errorMessage.setStatus(((WebApplicationException) ex).getResponse().getStatus());
        } else {
            errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()); //defaults to internal server error 500
        }
    }
}
