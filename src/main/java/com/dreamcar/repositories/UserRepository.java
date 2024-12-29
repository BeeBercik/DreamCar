package com.dreamcar.repositories;

import com.dreamcar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     *  Method checks if user exist in repository by its mail
     *
     * @param mail user mail
     * @return true if user exists, false otherwise
     */
    boolean existsByEmail(String mail);

    /**
     * Method checks if user exist in repository by its login
     *
     * @param login user login
     * @return true if user exists, false otherwise
     */
    boolean existsByLogin(String login);

    /**
     * Finds user in repository by its login
     *
     * @param login user login
     * @return found user object, null if not found
     */
    User findByLogin(String login);
}
