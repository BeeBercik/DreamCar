package com.dreamcar.services;

import com.dreamcar.dto.UserDTO;
import com.dreamcar.model.User;
import com.dreamcar.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserValidatior userValidatior;

    public void registerUser(UserDTO userDTO) {
        userValidatior.validateRegistration(userDTO);

        if(userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if(userRepository.existsByLogin(userDTO.getLogin())) {
            throw new IllegalArgumentException("Login already exists");
        }
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setAdd_date(new Date());

        userRepository.save(user);
    }

    public ResponseEntity<?> loginUser(User user) {
        this.userValidatior.validateLogin(user);

        if(userRepository.existsByLogin(user.getLogin().trim())) {
            User dbuser = userRepository.findByLogin(user.getLogin());
            if(dbuser.getPassword().equals(user.getPassword().trim())) {
                UserDTO userDTO = new UserDTO(dbuser.getId(),
                        dbuser.getLogin(), dbuser.getEmail(),
                        dbuser.getPhone(), dbuser.getAdd_date());

                return ResponseEntity.ok(userDTO);
            } else throw new IllegalArgumentException("Wrong password");
        } else throw new IllegalArgumentException("Login does not exist");
    }
}
