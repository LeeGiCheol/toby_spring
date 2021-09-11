package me.gicheol.main;

import me.gicheol.dao.CountingConnectionMaker;
import me.gicheol.dao.CountingDaoFactory;
import me.gicheol.dao.DaoFactory;
import me.gicheol.dao.UserDao;
import me.gicheol.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoCounnectionCountingTest {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao userDao = context.getBean("userDao", UserDao.class);

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

        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("connection counter : " + ccm.getCounter());


        userDao.deleteAll();

    }

}