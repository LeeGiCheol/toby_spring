package me.gicheol.main;

import me.gicheol.dao.ConnectionMaker;
import me.gicheol.dao.DConnectionMaker;
import me.gicheol.dao.NConnectionMaker;
import me.gicheol.dao.UserDao;
import me.gicheol.domain.User;

import java.sql.SQLException;

public class UserDaoTest {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ConnectionMaker connectionMaker = new NConnectionMaker();
        UserDao userDao = new UserDao(connectionMaker);

        User user = new User();
        user.setId("LEEGICHEOL");
        user.setName("기철");
        user.setPassword("asdfg");

        userDao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = userDao.get(user.getId());
        System.out.println("user2.getName() = " + user2.getName());
        System.out.println("user2.getPassword() = " + user2.getPassword());

        System.out.println(user2.getId() + " 조회 성공");
    }

}
