package fr.univtln.bruno.demos.jaxrs.resources;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import lombok.extern.java.Log;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

@Log
@Path("extrandomnumbers/")
public class ExtendedRandomNumbersResource extends RandomNumbersResource {

    // We need a signing key for the id token, so we'll create one just for this example. Usually
    // the key would be read from your application configuration instead.
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // We set a simple password for the demo
    private static byte[] passwordHash;

    private static byte[] salt;

    static {
        try {
            salt = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(salt);

            passwordHash = hash("secret");

        } catch (NoSuchAlgorithmException e) {
            log.severe("No such algorithm: " + e.getLocalizedMessage());
        } catch (InvalidKeySpecException e) {
            log.severe("Invalid key: " + e.getLocalizedMessage());
        }
    }

    private static byte[] hash(String string) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(string.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return factory.generateSecret(spec).getEncoded();
    }

    /**
     * curl -X GET "http://localhost:8080/myapp/extrandomnumbers/qp?index=0"
     *
     * @param index
     * @return
     */
    @GET
    @Path("qp")
    public int get(@QueryParam("index") int index) {
        return numberListDAO.get(index);
    }

    /**
     * curl -X POST http://localhost:8080/myapp/extrandomnumbers/pp/45
     *
     * @param value
     * @return
     */
    @Path("pp/{value}")
    @POST
    public String add(@PathParam("value") String value) {
        numberListDAO.add(Integer.parseInt(value));
        return value;
    }

    /**
     * curl -X POST http://localhost:8080/myapp/extrandomnumbers/145
     *
     * @param value
     * @return
     */
    @Path("{value : \\d+}")
    @POST
    public int add(@PathParam("value") int value) {
        numberListDAO.add(value);
        return value;
    }

    /**
     * curl -X POST "http://localhost:8080/myapp/extrandomnumbers/multiply"
     * curl -H "factor:3" -X POST "http://localhost:8080/myapp/extrandomnumbers/multiply"
     *
     * @param factor
     * @return
     */
    @Path("multiply")
    @POST
    public String multiply(@DefaultValue("2") @HeaderParam("factor") int factor) {
        return Arrays.toString(numberListDAO.multiply(factor));
    }

    /**
     * Curl doesn't send cookies so we save it to a file.
     * curl -v -H "Content-Type: text/plain" -X POST -d "secret" -c /tmp/cookies.txt "http://localhost:8080/myapp/extrandomnumbers/login"
     *
     * @param password
     * @return
     */
    @Path("login")
    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public Response login(String password) {
        Response.ResponseBuilder response;
        try {
            if (Arrays.equals(hash(password), passwordHash))
                //see https://jwt.io/ && https://github.com/jwtk/jjwt
                response = Response.ok()
                        .cookie(new NewCookie("token",
                                Jwts.builder()
                                        .setSubject("users/1")
                                        .claim("name", "Joe")
                                        .claim("role", "admin")
                                        .signWith(key).compact()));
            else
                response = Response.status(Response.Status.UNAUTHORIZED);
        } catch (Exception e) {
            response = Response.serverError();
        }
        return response.build();
    }

    /**
     * @param jwsString
     * @return
     */
    @Path("admin")
    @GET
    public Response admin(@CookieParam("token") String jwsString) {
        Jws<Claims> jws;
        Response.ResponseBuilder response;
        try {
            jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwsString);
            log.info(jws.toString());
            if (jws.getBody().get("role").equals("admin"))
                response = Response.ok().entity("You are admin !");
            else
                response = Response.status(Response.Status.UNAUTHORIZED);
        } catch (JwtException ex) {
            response = Response.serverError();
        }
        return response.build();
    }
}
