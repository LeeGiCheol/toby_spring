package me.gicheol.dao;

import me.gicheol.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userMapper = new RowMapper<>() {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
            return user;
        }
    };


    public void add(final User user) {
        this.jdbcTemplate.update("INSERT INTO users(id, name, password) VALUES (?, ?, ?)",
                user.getId(), user.getName(), user.getPassword());
    }


    public User get(String id) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", new Object[] {id}, this.userMapper);
    }


    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM users");
    }


    public int getCount() {
        return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM users");
    }


    public List<User> getAll() {
        return this.jdbcTemplate.query("SELECT * FROM users ORDER BY id", this.userMapper);
    }

}
