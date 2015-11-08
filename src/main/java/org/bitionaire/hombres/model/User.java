package org.bitionaire.hombres.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bitionaire.hombres.resources.UsersResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;

import java.io.Serializable;
import java.net.URI;

@ToString @EqualsAndHashCode(exclude = { "id", "self" })
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @Getter private final String id;
    @Getter private final String name;
    @Getter private final String email;

    @InjectLink(
            resource = UsersResource.class,
            style = InjectLink.Style.ABSOLUTE,
            method = "get",
            bindings = @Binding(name = "id", value = "${instance.id}"),
            rel = "self"
    )
    @Getter private URI self;

    public User(final String id, final String name, final String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    @JsonCreator
    public User(@JsonProperty("name") final String name,
                @JsonProperty("email") final String email) {
        this.id = null;
        this.name = name;
        this.email = email;
    }

}
