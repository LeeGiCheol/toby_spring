package me.gicheol.dao;

import me.gicheol.domain.Level;
import me.gicheol.domain.User;
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
            user.setEmail(resultSet.getString("email"));
            user.setLevel(Level.valueOf(resultSet.getInt("level")));
            user.setLogin(resultSet.getInt("login"));
            user.setRecommend(resultSet.getInt("recommend"));
            return user;
        }
    };

    @Override
    public void add(final User user) {
        this.jdbcTemplate.update("INSERT INTO users(id, name, password, email, level, login, recommend) VALUES (?, ?, ?, ?, ?, ?, ?)",
                user.getId(), user.getName(), user.getPassword(), user.getEmail(), user.getLevel().intValue(), user.getLogin(), user.getRecommend());
    }

    @Override
    public User get(String id) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", new Object[] {id}, this.userMapper);
    }

    @Override
    public List<User> getAll() {
        return this.jdbcTemplate.query("SELECT * FROM users ORDER BY id", this.userMapper);
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM users");
    }

    @Override
    public int getCount() {
        return this.jdbcTemplate.queryForInt("SELECT COUNT(*) FROM users");
    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update("UPDATE users SET name = ?, password = ?, email = ?, level = ?, login = ?, " +
                                     "recommend = ? WHERE id = ?",
                                    user.getName(), user.getPassword(), user.getEmail(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getId());
    }
}
