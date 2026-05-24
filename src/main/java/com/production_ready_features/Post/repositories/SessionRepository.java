package com.production_ready_features.Post.repositories;

import com.production_ready_features.Post.entities.Session;
import com.production_ready_features.Post.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session,Long> {
    List<Session> findByUser(User user);

    Optional<Session> findByRefreshToken(String refreshToken);
}
