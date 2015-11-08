package org.bitionaire.hombres.persistence;

import org.bitionaire.hombres.model.User;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(UserMapper.class)
public interface UserDAO {

    @SqlQuery("SELECT id, name, email FROM users WHERE id = :id")
    User find(@Bind("id") final String id);

    @SqlUpdate("INSERT INTO users (id, name, email) VALUES (:id, :name, :email)")
    void add(@Bind("id") final String id, @Bind("name") final String name, @Bind("email") final String email);
}
