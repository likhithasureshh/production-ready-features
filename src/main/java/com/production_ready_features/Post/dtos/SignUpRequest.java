package com.production_ready_features.Post.dtos;

import com.production_ready_features.Post.entities.enums.Role;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignUpRequest {
    String name;
    String email;
    String password;
    Set<Role> roles;
}
