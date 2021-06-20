package me.gicheol.dao;

public class DaoFactory {

    public UserDao userDao() {
        ConnectionMaker connectionMaker = new NConnectionMaker();
        return new UserDao(connectionMaker);
    }

}
