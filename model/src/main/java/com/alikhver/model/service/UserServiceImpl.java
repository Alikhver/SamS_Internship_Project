package com.alikhver.model.service;

import com.alikhver.model.entity.User;
import com.alikhver.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userExistsByLogin(String login) {
        return userRepository.existsUserByLogin(login);
    }

    // TODO refactor create user
    @Override
    public User createUser(User user) {
        Objects.requireNonNull(user.getLogin());
        Objects.requireNonNull(user.getPassword());
        Objects.requireNonNull(user.getRole());
        return userRepository.save(user);

    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userExistsById(Long id) {
        return userRepository.existsById(id);
    }

}
