package me.gicheol.service;

import me.gicheol.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserService {

    void add(User user);

    void update(User user);

    void upgradeLevels();

    void deleteAll();

    @Transactional(readOnly = true)
    User get(String id);

    @Transactional(readOnly = true)
    List<User> getAll();

}
