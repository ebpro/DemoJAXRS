package fr.univtln.bruno.demos.jaxrs.client;

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
