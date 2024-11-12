package com.dreamcar.repositories;

import com.dreamcar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public boolean existsByEmail(String mail);
    public boolean existsByLogin(String login);
}
