package api.service.auth.service;

import api.service.auth.entity.Session;

import java.util.List;
import java.util.Optional;

public interface SessionService {
    Session saveSession(Session session);
    List<Session> findAllSessions();
    Optional<Session> findSessionById(Long id);
    void deleteSession(Long id);
}
