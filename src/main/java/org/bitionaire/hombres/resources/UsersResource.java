package org.bitionaire.hombres.resources;

import com.google.common.base.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bitionaire.hombres.model.User;
import org.bitionaire.hombres.persistence.UserDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

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

    @POST
    @Path("/{id}")
    public Response add(@PathParam("id") final String id, final User user) {
        userDAO.add(id, user.getName(), user.getEmail());

        return Response.created(UriBuilder.fromPath("/users/{id}").build(id)).build();
    }

    @PUT
    @Path("/{id}")
    public Response put(@PathParam("id") final String id, final User user) {
        java.util.Optional.ofNullable(user.getName()).ifPresent(name -> userDAO.setName(id, name));
        java.util.Optional.ofNullable(user.getEmail()).ifPresent(name -> userDAO.setEmail(id, name));
        return Response.ok().build();
    }
}
