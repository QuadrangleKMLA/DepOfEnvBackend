package com.quadrangle.depofenvkmla.Payload.requestBody;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.quadrangle.depofenvkmla.Payload.Deserializer.StringSetDeserializer;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class SignupReq {
    private String username;
    private String email;
    @JsonDeserialize(using = StringSetDeserializer.class)
    private Set<String> role = new HashSet<>();
    private String password;
}
