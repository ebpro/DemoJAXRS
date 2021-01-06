package fr.univtln.bruno.demos.jaxrs.client;

/*-
 * #%L
 * JAXRS Client
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

import fr.univtln.bruno.demos.jaxrs.model.Person;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.util.Optional;


@Log
@NoArgsConstructor(staticName = "of")
public class MyClient {

    public static final String SERVER_IP;
    public static final int SERVER_PORT;
    private static final String REST_URI;

    static {
        SERVER_IP = Optional.ofNullable(System.getProperty("fr.univtln.bruno.demo.jaxrs.server.ip")).orElse("localhost");
        int port = 8080;
        try {
            port = Integer.parseInt(Optional.ofNullable(System.getProperty("fr.univtln.bruno.demo.jaxrs.server.port")).orElse("8080"));
        } catch (NumberFormatException e) {
            log.severe("Server port is not a number, using default value");
            System.exit(0);
        }
        SERVER_PORT = port;
        REST_URI = "http://" + SERVER_IP + ":" + SERVER_PORT + "/myapp/";
        log.info("Server URI:" + REST_URI);
    }

    private final Client client = ClientBuilder.newClient();

    public static void main(String[] args) {
        MyClient myClient = MyClient.of();
        Person person2 = myClient.getPerson(2);
        log.info(person2.toString());
    }

    public Person getPerson(int id) {
        return client
                .target(REST_URI)
                .path("persons")
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .get(Person.class);
    }

}
