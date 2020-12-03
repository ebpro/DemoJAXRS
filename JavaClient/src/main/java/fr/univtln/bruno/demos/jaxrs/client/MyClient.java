package fr.univtln.bruno.demos.jaxrs.client;

import fr.univtln.bruno.demos.jaxrs.model.Person;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;


@Log
@NoArgsConstructor(staticName = "of")
public class MyClient {
    private static String restUri = "http://localhost:8080/myapp/";
    private Client client = ClientBuilder.newClient();

    public static void main(String[] args) {
        MyClient myClient = MyClient.of();
        Person person2 = myClient.getPerson(2);
        log.info(person2.toString());
    }

    public Person getPerson(int id) {
        return client
                .target(restUri)
                .path("persons")
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .get(Person.class);
    }

}
