package com.alikhver.model.service;

import com.alikhver.model.entity.User;
import com.alikhver.model.repository.UserRepository;
import com.alikhver.model.service.util.ServiceValidationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ServiceValidationHelper validationHelper;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> get(Long userId) {
        validationHelper.validateForCorrectId(userId, "UserId");
        return userRepository.findById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByLogin(String login) {
        validationHelper.validateForCorrectString(login, "Login");
        return userRepository.existsUserByLogin(login);
    }

    @Override
    public User save(User user) {
        validationHelper.validateUser(user);
        return userRepository.save(user);
    }

    @Override
    public void delete(Long userId) {
        validationHelper.validateForCorrectId(userId, "UserId");
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userExistsById(Long userId) {
        validationHelper.validateForCorrectId(userId, "UserId");
        return userRepository.existsById(userId);
    }
}
