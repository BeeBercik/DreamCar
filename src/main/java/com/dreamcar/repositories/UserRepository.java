package com.dreamcar.repositories;

import com.dreamcar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String mail);
    boolean existsByLogin(String login);
    User findByLogin(String login);
}
