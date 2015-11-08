package org.bitionaire.hombres;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lombok.extern.slf4j.Slf4j;
import org.bitionaire.hombres.persistence.UserDAO;
import org.bitionaire.hombres.resources.UsersResource;
import org.flywaydb.core.Flyway;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.sqlobject.customizers.OverrideStatementLocatorWith;

import javax.ws.rs.core.Link;
import java.io.IOException;


@Slf4j
public class HombresApplication extends Application<HombresConfiguration> {

    public static void main(final String... args) throws Exception {
        new HombresApplication().run(args);
    }

    @Override
    public void initialize(final Bootstrap bootstrap) {
        final SimpleModule module = new SimpleModule();
        module.addSerializer(new LinkSerializer());
        bootstrap.getObjectMapper().registerModule(module);
    }

    @Override
    public void run(final HombresConfiguration configuration, final Environment environment) throws Exception {
        final DataSourceFactory database = configuration.getDatabase();
        this.executeDatabaseMigrations(database);

        // create DBI instance
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, database, "postgresql");

        // enable the linking feature of jersey
        environment.jersey().getResourceConfig().packages(getClass().getPackage().getName()).register(DeclarativeLinkingFeature.class);

        // register REST resources
        environment.jersey().register(new UsersResource(jdbi.onDemand(UserDAO.class)));
    }

    private void executeDatabaseMigrations(final DataSourceFactory database) {
        final Flyway flyway = new Flyway();
        flyway.setDataSource(database.getUrl(), database.getUser(), database.getPassword());
        log.debug("execute database migrations");
        flyway.migrate();
        log.info("database migrations successfully executed");
    }

    private static class LinkSerializer extends JsonSerializer<Link> {
        @Override
        public void serialize(final Link value, final JsonGenerator gen, final SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeStringField("rel", value.getRel());
            gen.writeStringField("href", value.getUri().toString());
            gen.writeEndObject();
        }

        @Override
        public Class<Link> handledType() {
            return Link.class;
        }
    }
}
