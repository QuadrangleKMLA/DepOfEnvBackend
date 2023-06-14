package com.quadrangle.depofenvkmla.Payload.responseBody;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class JwtRes {
    private String token;
    private String type = "Bearer ";
    private ObjectId id;
    private String username;
    private String email;
    private List<String> roles;

    public JwtRes(String token, ObjectId id, String username, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

}
