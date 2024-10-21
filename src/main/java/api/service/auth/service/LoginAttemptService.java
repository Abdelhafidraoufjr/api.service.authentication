package api.service.auth.service;

import api.service.auth.entity.LoginAttempt;

import java.util.List;
import java.util.Optional;

public interface LoginAttemptService {
    LoginAttempt saveLoginAttempt(LoginAttempt loginAttempt);
    List<LoginAttempt> findAllLoginAttempts();
    Optional<LoginAttempt> findLoginAttemptById(Long id);
    void deleteLoginAttempt(Long id);
}
