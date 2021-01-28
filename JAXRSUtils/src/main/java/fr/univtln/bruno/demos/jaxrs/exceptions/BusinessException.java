package fr.univtln.bruno.demos.jaxrs.exceptions;

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


import fr.univtln.bruno.utils.AppConstants;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.extern.java.Log;

/**
 * Created by bruno on 15/02/15.
 */
@Log
@Getter
public class BusinessException extends fr.univtln.bruno.utils.exceptions.BusinessException {
    /**
     * contains redundantly the HTTP status of the response sent back to the client in case of error, so that
     * the developer does not have to look into the response headers. If null a default
     */
    private Response.Status status;

    /**
     * @param status
     * @param businessErrorCode
     * @param message
     * @param developerMessage
     * @param link
     */
    public BusinessException(Response.Status status, AppConstants.ErrorCode businessErrorCode, String message,
                             String developerMessage, String link) {
        super(businessErrorCode, message, developerMessage, link);
        this.status = status;
    }

    public BusinessException(Throwable e) {
        this(Response.Status.INTERNAL_SERVER_ERROR, AppConstants.ErrorCode.GENERIC_EXCEPTION, e.getMessage(), null, null);
    }

}
