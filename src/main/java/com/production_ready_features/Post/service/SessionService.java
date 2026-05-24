package com.production_ready_features.Post.service;

import com.production_ready_features.Post.entities.Session;
import com.production_ready_features.Post.entities.User;
import com.production_ready_features.Post.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final int SESSION_MAX = 2;


    public void generateNewSession(User user,String refreshToken)
    {
        List<Session> sessionList =sessionRepository.findByUser(user);
        if(sessionList.size()==SESSION_MAX)
        {
            sessionList.sort(Comparator.comparing(Session::getLastUsedAt));
            Session toBeDeleted = sessionList.getFirst();
            sessionRepository.delete(toBeDeleted);
        }
        Session session = Session.builder()
                .user(user)
                .refreshToken(refreshToken)
                .lastUsedAt(LocalDateTime.now())
                .build();
        sessionRepository.save(session);

    }

    public void invalidate(String refreshToken)
    {
        Session session = sessionRepository.findByRefreshToken(refreshToken)
       .orElseThrow(()-> new SessionAuthenticationException("Session not found for refresh token: "+refreshToken));
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }
}
