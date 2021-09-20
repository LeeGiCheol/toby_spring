package me.gicheol.dao;

import me.gicheol.domain.Level;
import me.gicheol.domain.User;
import me.gicheol.exception.DuplicateUserIdException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoJdbc implements UserDao {

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
            user.setLevel(Level.valueOf(resultSet.getInt("level")));
            user.setLogin(resultSet.getInt("login"));
            user.setRecommand(resultSet.getInt("recommand"));
            return user;
        }
    };


    public void add(final User user) throws DuplicateUserIdException {
        try {
            this.jdbcTemplate.update("INSERT INTO users(id, name, password, level, login, recommand) VALUES (?, ?, ?, ?, ?, ?)",
                    user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommand());
        } catch (DuplicateUserIdException e) {
            throw new DuplicateUserIdException(e);
        }
    }


    public User get(String id) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", new Object[] {id}, this.userMapper);
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query("SELECT * FROM users ORDER BY id", this.userMapper);
    }

    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM users");
    }


    public int getCount() {
        return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM users");
    }

}
