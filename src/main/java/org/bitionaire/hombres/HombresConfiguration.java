package org.bitionaire.hombres;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class HombresConfiguration extends Configuration {

    @Valid @NotNull
    @JsonProperty("database")
    @Getter private DataSourceFactory database = new DataSourceFactory();

}
