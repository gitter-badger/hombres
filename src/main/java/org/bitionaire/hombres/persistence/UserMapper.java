package org.bitionaire.hombres.persistence;

import org.bitionaire.hombres.model.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ResultSetMapper<User> {

    @Override
    public User map(final int index, final ResultSet r, final StatementContext ctx) throws SQLException {
        return new User(r.getString("id"), r.getString("name"), r.getString("email"));
    }
}
