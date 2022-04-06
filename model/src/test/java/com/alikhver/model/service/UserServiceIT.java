package com.alikhver.model.service;

import com.alikhver.model.configuration.ModelConfigurationTest;
import com.alikhver.model.entity.User;
import com.alikhver.model.entity.UserRole;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ModelConfigurationTest.class,
        loader = AnnotationConfigContextLoader.class)
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        userService.deleteAll();

        User user = User.builder()
                .login("login")
                .password("password")
                .role(UserRole.USER)
                .build();

        userService.saveUser(user);
    }

    @After
    public void tearDown() {
        userService.deleteAll();
    }

    @Test
    public void getUser() {
        //Given
        User expected = userService.getUsers().get(0);

        //When
        User actual = userService.getUser(expected.getId()).get();

        //Then
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getLogin(), actual.getLogin());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getRole(), actual.getRole());
    }

    @Test
    public void existsUserByIdWhenExists() {
        //Given
        User user = userService.getUsers().get(0);

        //When
        boolean existsActual = userService.existsUserById(user.getId());

        //Then
        boolean existsExpected = true;

        assertEquals(existsExpected, existsActual);
    }

    @Test
    public void existsUserByIdWhenDoesNotExist() {
        //Given
        User user = userService.getUsers().get(0);

        //When
        boolean existsActual = userService.existsUserById(user.getId() + 1);

        //Then
        boolean existsExpected = false;

        assertEquals(existsExpected, existsActual);
    }

    @Test
    public void getUsers() {
        //Given
        userService.deleteAll();

        User user1 = User.builder()
                .login("login1")
                .password("password1")
                .role(UserRole.USER)
                .build();

        User user2 = User.builder()
                .login("login2")
                .password("password2")
                .role(UserRole.USER)
                .build();

        User user3 = User.builder()
                .login("login3")
                .password("password3")
                .role(UserRole.USER)
                .build();

        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);

        //When
        int sizeActual = userService.getUsers().size();

        //Then
        int sizeExpected = 3;
        assertEquals(sizeExpected, sizeActual);
    }

    @Test
    public void getUsersWithPagination() {
        //Given
        userService.deleteAll();

        User user1 = User.builder()
                .login("login1")
                .password("password1")
                .role(UserRole.USER)
                .build();

        User user2 = User.builder()
                .login("login2")
                .password("password2")
                .role(UserRole.USER)
                .build();

        User user3 = User.builder()
                .login("login3")
                .password("password3")
                .role(UserRole.USER)
                .build();

        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);

        Pageable pageable = PageRequest.of(0, 5);

        //When
        int sizeActual = userService.getUsers(pageable).getContent().size();

        //Then
        int sizeExpected = 3;
        assertEquals(sizeExpected, sizeActual);
    }

    @Test
    public void existsUserByLoginWhenExists() {
        //Given
        User expected = userService.getUsers().get(0);

        //When
        boolean existsActual = userService.existsUserByLogin(expected.getLogin());

        //Then
        boolean existsExpected = true;

        assertEquals(existsExpected, existsActual);
    }

    @Test
    public void existsUserByLoginWhenDoesNotExist() {
        //Given
        User expected = userService.getUsers().get(0);

        //When
        boolean existsActual = userService.existsUserByLogin(expected.getLogin() + "NotExists");

        //Then
        boolean existsExpected = false;

        assertEquals(existsExpected, existsActual);
    }

    @Test
    public void saveUserTest() {
        //Given
        userService.deleteAll();

        User expected = User.builder()
                .login("login")
                .password("password")
                .role(UserRole.USER)
                .build();

        //When
        userService.saveUser(expected);

        //Then
        User actual = userService.getUser(expected.getId()).get();

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getLogin(), actual.getLogin());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getRole(), actual.getRole());
    }

    @Test
    public void deleteUserTest() {
        //Given
        User user = userService.getUsers().get(0);

        //When
        userService.deleteUser(user.getId());

        //Then
        boolean existsActual = userService.existsUserByLogin(user.getLogin());
        boolean existsExpected = false;

        assertEquals(existsExpected, existsActual);
    }

    @Test
    public void deleteAllTest() {
        //Given
        User user = User.builder()
                .login("login1")
                .password("password1")
                .role(UserRole.USER)
                .build();

        userService.saveUser(user);

        int size = userService.getUsers().size();
        assertNotEquals(0, size);

        //When
        userService.deleteAll();

        //Then
        int sizeActual = userService.getUsers().size();
        int sizeExpected = 0;

        assertEquals(sizeExpected, sizeActual);
    }
}
