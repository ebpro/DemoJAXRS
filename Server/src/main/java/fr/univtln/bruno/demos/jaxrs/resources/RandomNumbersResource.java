package fr.univtln.bruno.demos.jaxrs.resources;

import fr.univtln.bruno.demos.jaxrs.model.NumberListDAO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.java.Log;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Root resource (exposed at "message" path)
 */
@Log
@Path("randomnumbers/")
@Produces(MediaType.TEXT_PLAIN)
public class RandomNumbersResource {
    protected NumberListDAO numberListDAO = NumberListDAO.of();

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

    @POST
    public String append() {
        final int newInt = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        numberListDAO.add(newInt);
        return String.valueOf(newInt);
    }

    @PUT
    public String start() {
        numberListDAO.clear();
        return append();
    }

    @DELETE
    public void delete() {
        numberListDAO.clear();
    }

    @HEAD
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMetadata() {
        return Response.status(Response.Status.OK)
                .header("last_update", numberListDAO.getChangeTime())
                //.entity(message)
                .build();
    }

}
