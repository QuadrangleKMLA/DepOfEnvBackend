package com.quadrangle.depofenvkmla.Documents.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByName(String name);
    List<User> findAllUsers();
    void deleteUser(String name, int wave);
}