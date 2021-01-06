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

import fr.univtln.bruno.demos.jaxrs.model.Person;
import fr.univtln.bruno.demos.jaxrs.model.PersonModel;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

/**
 * The type Person resource.
 */
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(MediaType.APPLICATION_JSON)
@Path("persons")
public class PersonResource {

    /**
     * curl -v "http://localhost:8080/myapp/persons/2"
     *
     * @param id the id
     * @return person
     */
    @GET
    @Path("{id}")
    public Person get(@PathParam("id") int id) {
        return PersonModel.get(id);
    }

    /**
     * curl -v "http://localhost:8080/myapp/persons/"
     *
     * @return list
     */
    @GET
    public List<Person> get() {
        return PersonModel.get();
    }

    /**
     * curl -v --header "Content-Type: application/json" --request PUT --data '{"name":"Marie","email":"marie@ici.fr"}' http://localhost:8080/myapp/persons
     *
     * @param person the person
     * @return person
     */
    @PUT
    public Person put(Person person) {
        return PersonModel.put(person);
    }
}
