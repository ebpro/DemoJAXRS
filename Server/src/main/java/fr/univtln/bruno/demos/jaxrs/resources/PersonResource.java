package fr.univtln.bruno.demos.jaxrs.resources;

import fr.univtln.bruno.demos.jaxrs.model.Person;
import fr.univtln.bruno.demos.jaxrs.model.PersonModel;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(MediaType.APPLICATION_JSON)
@Path("persons")
public class PersonResource {

    /**
     * curl -v "http://localhost:8080/myapp/persons/2"
     *
     * @param id
     * @return
     */
    @GET
    @Path("{id}")
    public Person get(@PathParam("id") int id) {
        return PersonModel.get(id);
    }

    /**
     * curl -v "http://localhost:8080/myapp/persons/"
     *
     * @return
     */
    @GET
    public List<Person> get() {
        return PersonModel.get();
    }

    /**
     * curl -v --header "Content-Type: application/json" --request PUT --data '{"name":"Marie","email":"marie@ici.fr"}' http://localhost:8080/myapp/persons
     *
     * @param person
     * @return
     */
    @PUT
    public Person put(Person person) {
        return PersonModel.put(person);
    }
}
