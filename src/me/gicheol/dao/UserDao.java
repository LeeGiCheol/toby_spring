package me.gicheol.dao;

import me.gicheol.domain.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void add(User user) throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "INSERT INTO users (id, name, password) values(?, ?, ?)");

        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }


    public User get(String id) throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "SELECT * FROM users WHERE id = ?");

        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        User user = null;

        if (rs.next()) {
            user = new User();

            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();

        if (user == null) {
            throw new RuntimeException("유저 정보가 없습니다.");
        }

        return user;
    }


    public void deleteAll() throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("DELETE FROM users");
        ps.execute();
        ps.close();
        c.close();
    }

    public int getCount() throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM users");

        ResultSet rs = ps.executeQuery();
        rs.next();

        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }

}
