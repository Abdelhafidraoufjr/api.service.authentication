package api.service.auth.service;

import api.service.auth.entity.Session;
import api.service.auth.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public Session saveSession(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public List<Session> findAllSessions() {
        return sessionRepository.findAll();
    }

    @Override
    public Optional<Session> findSessionById(Long id) {
        return sessionRepository.findById(id);
    }

    @Override
    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }
}
