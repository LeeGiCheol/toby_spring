package me.gicheol.service;

import me.gicheol.domain.User;

import java.util.List;

public interface UserService {

    void add(User user);

    void upgradeLevels();

    void deleteAll();

    User get(String id);

    List<User> getAll();

    void update(User user);

}
