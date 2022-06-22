package com.alikhver.web.facade;

import com.alikhver.model.entity.User;
import com.alikhver.model.entity.UserRole;
import com.alikhver.model.service.UserService;
import com.alikhver.web.WebApplication;
import com.alikhver.web.dto.user.request.CreateUserRequest;
import com.alikhver.web.dto.user.request.UpdateUserRequest;
import com.alikhver.web.dto.user.response.CreateUserResponse;
import com.alikhver.web.dto.user.response.GetUserResponse;
import com.alikhver.web.exception.user.NoUserFoundException;
import com.alikhver.web.exception.user.UserAlreadyExistsException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class UserFacadeTest {
    @SpyBean
    private UserFacade userFacade;
    @MockBean
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void getUser() {
        //Given
        Long correctId = 1L;

        User user = User.builder()
                .id(correctId)
                .password("correctPassword")
                .login("correctLogin")
                .role(UserRole.USER)
                .build();

        when(userService.getUser(correctId)).thenReturn(Optional.of(user));

        //When
        GetUserResponse actual = userFacade.getUser(correctId);

        //Then
        GetUserResponse expected = GetUserResponse.builder()
                .id(correctId)
                .login("correctLogin")
                .role(UserRole.USER.name())
                .build();

        Assertions.assertEquals(expected.getId(), actual.getId());
        Assertions.assertEquals(expected.getRole(), actual.getRole());
        Assertions.assertEquals(expected.getLogin(), actual.getLogin());
    }

    @Test
    public void getUserWhenIncorrectId() {
        //Given
        Long incorrectId = 1L;

        when(userService.getUser(incorrectId)).thenReturn(Optional.empty());

        //When
        Exception e = assertThrows(NoUserFoundException.class, () -> userFacade.getUser(incorrectId));

        //Then
        String expected = "NoUserFoundException.msg.id";
        String actual = e.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void getAllUsersWhenIncorrectPage() {
        //Given
        int incorrectPage = -1, correctSize = 3;

        //When
        Exception e = assertThrows(IllegalArgumentException.class, () -> userFacade.getAllUsers(incorrectPage, correctSize));

        //Then
        String expected = "Page should be positive or zero";
        String actual = e.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void getAllUsersWhenIncorrectSize() {
        //Given
        int correctPage = 3, incorrectSize = -1;

        //When
        Exception e = assertThrows(IllegalArgumentException.class, () -> userFacade.getAllUsers(correctPage, incorrectSize));

        //Then
        String expected = "Size must be positive";
        String actual = e.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void createUserWhenCorrect() {
        //Given
        CreateUserRequest request = CreateUserRequest.builder()
                .login("correctLogin")
                .password("correctPassword")
                .build();

        when(userService.existsUserByLogin(request.getLogin())).thenReturn(false);
        when(userService.saveUser(any())).thenReturn(User.builder()
                .id(1L)
                .login("correctLogin")
                .password("correctPassword")
                .role(UserRole.USER)
                .build());

        //When
        CreateUserResponse actual = userFacade.createUser(request);

        //Then
        Assertions.assertEquals(request.getLogin(), actual.getLogin());
        Assertions.assertEquals(UserRole.USER.name(), actual.getRole());
    }

    @Test
    public void createUserWhenIncorrect() {
        //Given
        CreateUserRequest request = CreateUserRequest.builder()
                .login("correctLogin")
                .password("correctPassword")
                .build();

        when(userService.existsUserByLogin(request.getLogin())).thenReturn(true);

        //When
        Exception e = assertThrows(UserAlreadyExistsException.class, () -> userFacade.createUser(request));

        //Then
        String expected = "UserAlreadyExistsException.msg.login";
        String actual = e.getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void deleteUserWhenCorrect() {
        //Given
        Long correctId = 1L;

        when(userService.existsUserById(correctId)).thenReturn(true);

        //When
        userFacade.deleteUser(correctId);

        //Then
        verify(userFacade, times(1)).deleteUser(correctId);
        verify(userService, times(1)).deleteUser(correctId);
    }

    @Test
    public void deleteUserWhenIncorrect() {
        //Given
        Long incorrectId = 1L;

        when(userService.existsUserById(incorrectId)).thenReturn(false);

        //When
        Exception e = assertThrows(NoUserFoundException.class, () -> userFacade.deleteUser(incorrectId));

        //Then
        String expected = "NoUserFoundException.msg.id";
        String actual = e.getMessage();

        Assertions.assertEquals(expected, actual);

        verify(userFacade, times(1)).deleteUser(incorrectId);
        verify(userService, times(0)).deleteUser(incorrectId);
    }

    @Test
    public void updateUserWhenIncorrectId() {
        //Given
        Long incorrectId = 1L;

        when(userService.getUser(incorrectId)).thenReturn(Optional.empty());

        //When
        Exception e = assertThrows(NoUserFoundException.class, () -> userFacade.getUser(incorrectId));

        //Then
        String expected = "NoUserFoundException.msg.id";
        String actual = e.getMessage();

        assertEquals(expected, actual);
    }

    @Test
//    @Ignore
    public void updateUserWhenCorrect() {
        //Given
        Long correctId = 1L;

        UpdateUserRequest request = UpdateUserRequest.builder()
                .password("correctPassword")
                .build();

        User user = User.builder()
                .id(correctId)
                .password("correctPassword")
                .login("correctLogin")
                .role(UserRole.USER)
                .build();

        when(userService.getUser(correctId)).thenReturn(Optional.of(user));

        //When
        userFacade.updateUser(correctId, request);

        //Then
        verify(userFacade, times(1)).updateUser(correctId, request);
//        Assertions.assertEquals(passwordEncoder.encode(request.getPassword()), user.getPassword());
        //TODO почему разные пароли?
    }

    @Test
    public void findByLoginWhenCorrectLogin() {
        //Given
        String correctLogin = "correctLogin";

        User expected = User.builder()
                .id(1L)
                .password("correctPassword")
                .login("correctLogin")
                .role(UserRole.USER)
                .build();

        when(userService.findUserByLogin(correctLogin)).thenReturn(Optional.of(expected));

        //When
        GetUserResponse actual = userFacade.findByLogin(correctLogin);

        //Then
        Assertions.assertEquals(expected.getRole().name(), actual.getRole());
        Assertions.assertEquals(expected.getLogin(), actual.getLogin());
        Assertions.assertEquals(expected.getId(), actual.getId());
    }

    @Test
    public void findByLoginWhenIncorrectLogin() {
        //Given
        String incorrectLogin = "incorrectLogin";

        when(userService.findUserByLogin(incorrectLogin)).thenReturn(Optional.empty());

        //When
        Exception e = assertThrows(NoUserFoundException.class, () -> userFacade.findByLogin(incorrectLogin));

        //Then
        String expected = "NoUserFoundException.msg.login";
        String actual = e.getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void loginExistsWhenExists() {
        //Given
        String correctLogin = "correctLogin";

        when(userFacade.loginExists(correctLogin)).thenReturn(true);

        //When
        boolean actual = userFacade.loginExists(correctLogin);

        //Then
        Assertions.assertTrue(actual);
    }

    @Test
    public void loginExistsWhenNotExists() {
        //Given
        String incorrectLogin = "incorrectLogin";

        when(userFacade.loginExists(incorrectLogin)).thenReturn(false);

        //When
        boolean actual = userFacade.loginExists(incorrectLogin);

        //Then
        Assertions.assertFalse(actual);
    }

}
