package com.quadrangle.depofenvkmla.Documents.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserServices userServices;

    @GetMapping("/get")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userServices.allUsers(), HttpStatus.OK);
    }

    @GetMapping("/get/{name}")
    public ResponseEntity<Optional<User>> getUserByName(@PathVariable String name) {
        return new ResponseEntity<>(userServices.findUserByName(name), HttpStatus.OK);
    }

    @PostMapping("/push")
    public ResponseEntity<User> createUser(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<>(userServices.createUser(payload.get("name"), Integer.parseInt(payload.get("wave")), payload.get("telephone"), payload.get("email"), payload.get("password")), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<>(userServices.deleteUser(payload.get("name"), Integer.parseInt(payload.get("wave"))), HttpStatus.OK);
    }

    @PutMapping("/update/tel")
    public ResponseEntity<User> updateUserTel(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<>(userServices.updateUserTel(payload.get("telephone"), payload.get("name"), Integer.parseInt(payload.get("wave"))), HttpStatus.OK);
    }

    @PutMapping("/update/email")
    public ResponseEntity<User> updateUserEmail(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<>(userServices.updateUserEmail(payload.get("email"), payload.get("name"), Integer.parseInt(payload.get("wave"))), HttpStatus.OK);
    }
}
