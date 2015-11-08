package org.bitionaire.hombres.persistence;

import org.bitionaire.hombres.model.User;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(UserMapper.class)
public interface UserDAO {

    @SqlQuery("SELECT id, name, email FROM users WHERE id = :id")
    User find(@Bind("id") final String id);
}
