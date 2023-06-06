package com.quadrangle.depofenvkmla.Documents.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "USER")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private boolean isCaptain = false;

    private int wave;

    @Indexed(unique = true)
    private String tel;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String password;

    private final String role;

    public User(String name, int wave, String tel, String email, String password) {
        this.name = name;
        this.wave = wave;
        this.tel = tel;
        this.email = email;
        this.password = password;
        role = "departmentAdmin";
    }

    public User(String name, int wave) {
        this.name = name;
        this.wave = wave;
        role = "departmentAdmin";
    }

    public String toString() {
        return String.format("User[name='%s', wave='%d']",name, wave);
    }
}
