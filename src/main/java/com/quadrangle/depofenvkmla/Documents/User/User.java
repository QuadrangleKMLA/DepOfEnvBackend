package com.quadrangle.depofenvkmla.Documents.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "USER")
@Data
@NoArgsConstructor
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

    public User(String name, int wave, String tel, String email, String password) {
        this.name = name;
        this.wave = wave;
        this.tel = tel;
        this.email = email;
        this.password = password;
    }

    public User(String name, int wave) {
        this.name = name;
        this.wave = wave;
    }

    public String toString() {
        return String.format("User[name='%s', wave='%d']",name, wave);
    }
}
