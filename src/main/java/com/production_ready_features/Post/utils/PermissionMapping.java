package com.production_ready_features.Post.utils;

import com.production_ready_features.Post.entities.enums.Permission;
import com.production_ready_features.Post.entities.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.production_ready_features.Post.entities.enums.Permission.*;
import static com.production_ready_features.Post.entities.enums.Role.*;

public class PermissionMapping {
    private static final Map<Role, Set<Permission>> map = Map.of(
            USER,Set.of(POST_VIEW,USER_VIEW),
            CREATOR,Set.of(POST_CREATE,USER_CREATE,POST_UPDATE,USER_UPDATE),
            ADMIN,Set.of(USER_DELETE,POST_DELETE)
    );

    public static Set<SimpleGrantedAuthority> getPermissionsForRoles(Role role)
    {
        return map.get(role)
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}
