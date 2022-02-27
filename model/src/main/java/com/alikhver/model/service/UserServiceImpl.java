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
    public User getUser(Long id) {
        // TODO use spring converter
        if (userRepository.existsById(id)) {
            return userRepository.getById(id);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean userExistsByLogin(String login) {
//        return userRepository.;
        return false;
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
    public void updateUser(Long id, User user) {

//        userRepository.findById(id)
//                .map(user -> {
//                    user.set
//                })
//                //save
    }

    @Override
    public void deleteUser(Long id) {

    }



    @Override
    @Transactional(readOnly = true)
    public boolean userExistsById(Long id) {
        // TODO use spring converter
        return userRepository.existsById(id);
    }

}
