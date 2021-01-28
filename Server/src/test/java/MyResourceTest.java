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

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import lombok.extern.java.Log;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Log
@ExtendWith(ServerTestExtension.class)
public class MyResourceTest {
    private static HttpServer server;
    private static WebTarget myresource;

    @BeforeAll
    public static void setUp() {
        log.info("Creating web target");
        myresource = ClientBuilder.newClient().target("http://localhost:8080/myapp/myresource");
    }

    @Test
    public void testGet() {
        Response response = myresource.request().get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Got it!", response.readEntity(String.class));
    }

}
