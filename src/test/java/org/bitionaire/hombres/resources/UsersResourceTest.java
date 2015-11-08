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

        assertEquals(String.format("http://localhost:%d/users/%s", RULE.getLocalPort(), user.getName()), location.get().toString());
        final User retrievedUser = client.target(location.get()).request().get(User.class);

        assertEquals(user, retrievedUser);
    }
}