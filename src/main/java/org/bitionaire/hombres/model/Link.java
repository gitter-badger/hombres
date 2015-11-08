package org.bitionaire.hombres.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.net.URI;

/** FIXME replace by {@link javax.ws.rs.core.Link} */
public class Link {

    @JsonProperty("rel")
    @Getter private final String rel;

    @JsonProperty("href")
    @Getter private final URI href;

    @JsonCreator
    public Link(@JsonProperty("rel") final String rel, @JsonProperty("href") final URI href) {
        this.rel = rel;
        this.href = href;
    }
}
