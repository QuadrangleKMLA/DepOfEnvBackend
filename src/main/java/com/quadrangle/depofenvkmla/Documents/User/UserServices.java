package com.quadrangle.depofenvkmla.Documents.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserByName(String name) throws UsernameNotFoundException {
        return userRepository.findByName(name);
    }

    public User createUser(String name, int wave, String tel, String email, String password) {
        String pwd = passwordEncoder.encode(password);

        return userRepository.save(new User(name, wave, tel, email, pwd));
    }

    public String deleteUser(String name, int wave) {


        assert findUserByName(name).isPresent();
        userRepository.delete(findUserByName(name).get());

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
