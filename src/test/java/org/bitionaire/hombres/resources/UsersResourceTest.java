package org.bitionaire.hombres.resources;

import io.dropwizard.testing.junit.DropwizardAppRule;
import org.bitionaire.hombres.HombresApplication;
import org.bitionaire.hombres.HombresConfiguration;
import org.bitionaire.hombres.model.User;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.URI;
import java.util.Optional;

import static org.junit.Assert.*;
import static io.dropwizard.testing.ResourceHelpers.*;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UsersResourceTest {

    @ClassRule
    public static final DropwizardAppRule<HombresConfiguration> RULE = new DropwizardAppRule<>(HombresApplication.class, resourceFilePath("test.yml"));


    @Test
    public void testAddAndGetUser() throws Exception {
        final User user = new User("bitionaire", "info@bitionaire.org");
        final Entity entity = Entity.entity(user, MediaType.APPLICATION_JSON);

        final Client client = ClientBuilder.newClient();
        final Response response = client.target(String.format("http://localhost:%d/users/%s", RULE.getLocalPort(), user.getName())).request().post(entity);
        assertEquals(Response.Status.Family.SUCCESSFUL, response.getStatusInfo().getFamily());

        final Optional<URI> location = Optional.ofNullable(response.getLocation());
        assertTrue(location.isPresent());

        final String selfLink = String.format("http://localhost:%d/users/%s", RULE.getLocalPort(), user.getName());
        assertEquals(selfLink, location.get().toString());

        given()
                .port(RULE.getLocalPort())
        .when()
                .get(String.format("/users/%s", user.getName()))
        .then().assertThat()
                .body("name", is(equalTo(user.getName()))).and()
                .body("email", is(equalTo(user.getEmail()))).and()
                .body("links.find { it.rel == 'self' }.href", is(equalTo(selfLink)));
    }
}