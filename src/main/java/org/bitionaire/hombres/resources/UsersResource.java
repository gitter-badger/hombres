package org.bitionaire.hombres.resources;

import com.google.common.base.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bitionaire.hombres.model.User;
import org.bitionaire.hombres.persistence.UserDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Slf4j
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {

    private final UserDAO userDAO;

    public UsersResource(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GET
    @Path("/{id}")
    public Optional<User> get(@PathParam("id") final String id) {
        return Optional.fromNullable(userDAO.find(id));
    }

}
