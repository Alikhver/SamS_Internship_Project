package com.alikhver.model.service;

import com.alikhver.model.entity.User;
import com.alikhver.model.repository.UserRepository;
import com.alikhver.model.util.ValidationHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ValidationHelper validationHelper;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUser(Long userId) {
        log.info("userService::getUser -> start");
        validationHelper.validateForCorrectId(userId, "UserId");

        log.info("userService::getUser -> done");
        return userRepository.findById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> getUsers(Pageable pageable) {
        log.info("userService::getUsers -> start");

        var users = userRepository.findAll(pageable);

        log.info("userService::getUsers -> done");
        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsUserByLogin(String login) {
        log.info("userService::existsUserByLogin -> done");

        validationHelper.validateForCorrectString(login, "Login");
        boolean exists = userRepository.existsUserByLogin(login);

        log.info("userService::existsUserByLogin -> done");
        return exists;
    }

    @Override
    public User saveUser(User user) {
        log.info("userService::saveUser -> start");

        validateUser(user);

        log.info("userService::saveUser -> done");
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("userService::deleteUser -> start");

        validationHelper.validateForCorrectId(userId, "UserId");

        log.info("userService::deleteUser -> done");
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsUserById(Long userId) {
        log.info("userService::existsUserById -> start");

        validationHelper.validateForCorrectId(userId, "UserId");

        log.info("userService::existsUserById -> done");
        return userRepository.existsById(userId);
    }

    private void validateUser(User user) {
        log.info("userService::validateUser -> start");

        validationHelper.validateForCorrectString(user.getLogin(), "User Login");
        validationHelper.validateForCorrectString(user.getPassword(), "User Password");
        Objects.requireNonNull(user.getRole());

        log.info("userService::validateUser -> done");
    }
}
