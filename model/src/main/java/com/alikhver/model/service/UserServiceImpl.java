package com.alikhver.model.service;

import com.alikhver.model.entity.User;
import com.alikhver.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ConversionService conversionService;

    @Override
    @Transactional(readOnly = true)
    public User getUser(String id) {
        // TODO use spring converter
        if (userRepository.existsById(Objects.requireNonNull(conversionService.convert(id, Long.class)))) {
            return userRepository.getById(Objects.requireNonNull(conversionService.convert(id, Long.class)));
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        // TODO use spring converter
        return userRepository.findAll();
    }

    @Override
    public boolean userExistsByLogin(String id) {
        // TODO use spring converter
//        return userRepository.;
        return false;
    }

    @Override
    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUser(String id) {

    }

    @Override
    @Transactional(readOnly = true)
    public boolean userExistsById(String id) {
        // TODO use spring converter
        return userRepository.existsById(Long.parseLong(id));
    }

}
