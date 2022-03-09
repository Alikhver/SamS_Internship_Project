package com.alikhver.model.service;

import com.alikhver.model.entity.User;
import com.alikhver.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> get(Long userId) {
        if (userId > 0) {
            return userRepository.findById(userId);
        } else {
            throw new IllegalArgumentException(
                    "Illegal Argument: user id <= 0"
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByLogin(String login) {
        Objects.requireNonNull(login);
        return userRepository.existsUserByLogin(login);
    }

    @Override
    public User save(User user) {
        Objects.requireNonNull(user.getLogin());
        Objects.requireNonNull(user.getPassword());
        Objects.requireNonNull(user.getRole());
        return userRepository.save(user);

    }

    @Override
    public void delete(Long userId) {
        if (userId > 0) {
            userRepository.deleteById(userId);
        } else {
            throw new IllegalArgumentException(
                    "Illegal Argument: userId <= 0"
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userExistsById(Long userId) {
        if (userId > 0) {
            return userRepository.existsById(userId);
        } else {
            throw new IllegalArgumentException(
                    "Illegal Argument: userId <= 0"
            );
        }
    }
}
