package me.gicheol.service;

import me.gicheol.domain.User;

public interface UserService {

    void add(User user);

    void upgradeLevels();

}
