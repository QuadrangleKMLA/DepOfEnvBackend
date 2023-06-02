package com.quadrangle.depofenvkmla.Documents.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    public List<User> allUsers() {
        return userRepository.findAllUsers();
    }

    public Optional<User> findUserByName(String name) {
        return userRepository.findByName(name);
    }

    public User createUser(String name, int wave, String tel, String email, String password) {
        return userRepository.insert(new User(name, wave, tel, email, password));
    }

    public String deleteUser(String name, int wave) {
        userRepository.deleteUser(name, wave);

        if (userRepository.findByName(name).isEmpty()) {
            return "Successfully Deleted";
        } else {
            return "User " + name + wave + " wasn't deleted";
        }
    }

    public User updateUserTel(String updateBody, String name, int wave) {
        User target = userRepository.findByName(name).isPresent() ? userRepository.findByName(name).get() : new User(name, wave);

        target.setTel(updateBody);

        return userRepository.save(target);
    }

    public User updateUserEmail(String updateBody, String name, int wave) {
        User target = userRepository.findByName(name).isPresent() ? userRepository.findByName(name).get() : new User(name, wave);

        target.setEmail(updateBody);

        return userRepository.save(target);
    }
}
