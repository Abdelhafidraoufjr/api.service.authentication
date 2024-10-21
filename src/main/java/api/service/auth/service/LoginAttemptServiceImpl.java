package api.service.auth.service;

import api.service.auth.entity.LoginAttempt;
import api.service.auth.repository.LoginAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {

    @Autowired
    private LoginAttemptRepository loginAttemptRepository;

    @Override
    public LoginAttempt saveLoginAttempt(LoginAttempt loginAttempt) {
        return loginAttemptRepository.save(loginAttempt);
    }

    @Override
    public List<LoginAttempt> findAllLoginAttempts() {
        return loginAttemptRepository.findAll();
    }

    @Override
    public Optional<LoginAttempt> findLoginAttemptById(Long id) {
        return loginAttemptRepository.findById(id);
    }

    @Override
    public void deleteLoginAttempt(Long id) {
        loginAttemptRepository.deleteById(id);
    }
}
