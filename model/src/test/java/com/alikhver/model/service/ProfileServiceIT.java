package com.alikhver.model.service;

import com.alikhver.model.configuration.ModelConfigurationTest;
import com.alikhver.model.entity.Profile;
import com.alikhver.model.entity.User;
import com.alikhver.model.entity.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ModelConfigurationTest.class,
        loader = AnnotationConfigContextLoader.class)
public class ProfileServiceIT {
    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        profileService.deleteAll();

        User user = User.builder()
                .login("Login")
                .password("Password")
                .role(UserRole.USER)
                .build();

        Profile profile = Profile.builder()
                .firstName("Dzmitry")
                .lastName("Alikhver")
                .phoneNumber("+32 2 702-9200")
                .dateCreated(new Date())
                .email("dolihver@gmail.com")
                .user(user)
                .build();

        profileService.save(profile);
    }

    @Test
    @Transactional
    public void save() {
        //Given
        profileService.deleteAll();

        User user = User.builder()
                .login("Login")
                .password("Password")
                .role(UserRole.USER)
                .build();

        Profile expected = Profile.builder()
                .firstName("Dzmitry")
                .lastName("Alikhver")
                .phoneNumber("+32 2 702-9200")
                .dateCreated(new Date())
                .email("dolihver@gmail.com")
                .user(user)
                .build();

        //When
        profileService.save(expected);

        //Then
        //TODO refactor without warning
        Profile actual = profileService.getProfile(expected.getId()).get();

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getRecords(), actual.getRecords());
        assertEquals(expected.getDateCreated(), actual.getDateCreated());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getUser().getId(), actual.getUser().getId());
        assertEquals(expected.getUser().getRole(), actual.getUser().getRole());
        assertEquals(expected.getUser().getPassword(), actual.getUser().getPassword());
        assertEquals(expected.getUser().getLogin(), actual.getUser().getLogin());
    }


    @Test
    public void existsProfileByIdWhenExists() {
        //Given
        Profile expected = profileService.getAllProfiles().get(0);

        //When
        boolean existsActual = profileService.existsProfileById(expected.getId());

        //Then
        boolean existsExpected = true;
        assertEquals(existsExpected, existsActual);
    }

    @Test
    public void existsProfileByIdWhenDoesNotExist() {
        //Given
        Profile expected = profileService.getAllProfiles().get(0);

        //When
        boolean existsActual = profileService.existsProfileById(expected.getId() + 1);

        //Then
        boolean existsExpected = false;
        assertEquals(existsExpected, existsActual);
    }

    @Test
    @Transactional
    public void getProfileWhenExistsTest() {
        //Given
        Profile expected = profileService.getAllProfiles().get(0);

        //When
        Profile actual = profileService.getProfile(expected.getId()).get();

        //Then
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getRecords(), actual.getRecords());
        assertEquals(expected.getDateCreated(), actual.getDateCreated());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getUser().getId(), actual.getUser().getId());
        assertEquals(expected.getUser().getRole(), actual.getUser().getRole());
        assertEquals(expected.getUser().getPassword(), actual.getUser().getPassword());
        assertEquals(expected.getUser().getLogin(), actual.getUser().getLogin());
    }

    @Test
    @Transactional
    public void getProfileWhenDoesNotExistTest() {
        //Given
        Profile expected = profileService.getAllProfiles().get(0);

        //When
        Optional<Profile> actual = profileService.getProfile(expected.getId() + 1);

        //Then
        assertFalse(actual.isPresent());
    }

    @Test
    public void getAllProfiles() {
        profileService.deleteAll();

        User user = User.builder()
                .login("Login")
                .password("Password")
                .role(UserRole.USER)
                .build();

        Profile profile = Profile.builder()
                .firstName("Dzmitry")
                .lastName("Alikhver")
                .phoneNumber("+32 2 702-9200")
                .dateCreated(new Date())
                .email("dolihver@gmail.com")
                .user(user)
                .build();

        profileService.save(profile);

        User user1 = User.builder()
                .login("Login")
                .password("Password")
                .role(UserRole.USER)
                .build();

        Profile profile1 = Profile.builder()
                .firstName("Dzmitry")
                .lastName("Alikhver")
                .phoneNumber("+32 2 702-9200")
                .dateCreated(new Date())
                .email("dolihver@gmail.com")
                .user(user1)
                .build();

        profileService.save(profile1);

        //When
        int sizeActual = profileService.getAllProfiles().size();

        //Then
        int sizeExpected = 2;
        assertEquals(sizeExpected, sizeActual);
    }

    @Test
    public void getAllProfilesWithPagination() {
        profileService.deleteAll();

        User user = User.builder()
                .login("Login")
                .password("Password")
                .role(UserRole.USER)
                .build();

        Profile profile = Profile.builder()
                .firstName("Dzmitry")
                .lastName("Alikhver")
                .phoneNumber("+32 2 702-9200")
                .dateCreated(new Date())
                .email("dolihver@gmail.com")
                .user(user)
                .build();

        profileService.save(profile);

        User user1 = User.builder()
                .login("Login")
                .password("Password")
                .role(UserRole.USER)
                .build();

        Profile profile1 = Profile.builder()
                .firstName("Dzmitry")
                .lastName("Alikhver")
                .phoneNumber("+32 2 702-9200")
                .dateCreated(new Date())
                .email("dolihver@gmail.com")
                .user(user1)
                .build();

        profileService.save(profile1);

        Pageable pageable = PageRequest.of(0, 2);

        //When
        int sizeActual = profileService.getAllProfiles(pageable).getSize();

        //Then
        int sizeExpected = 2;
        assertEquals(sizeExpected, sizeActual);
    }

    @Test
    public void isUserAlreadyBoundedWhenBounded() {
        //Given
        Profile profile = profileService.getAllProfiles().get(0);
        User user = profile.getUser();

        //When
        boolean actual = profileService.isUserAlreadyBounded(user.getId());

        //Then
        boolean expected = true;

        assertEquals(expected, actual);
    }

    @Test
    public void isUserAlreadyBoundedWhenIsNotBounded() {
        //Given
        Profile profile = profileService.getAllProfiles().get(0);
        User user = profile.getUser();

        //When
        boolean actual = profileService.isUserAlreadyBounded(user.getId() + 1);

        //Then
        boolean expected = false;

        assertEquals(expected, actual);
    }

    @Test
    public void deleteAllTest() {
        //Given
        int size = profileService.getAllProfiles().size();
        assertNotEquals(0, size);

        //When
        profileService.deleteAll();

        //Then
        int sizeExpected = 0;
        int sizeActual = profileService.getAllProfiles().size();

        assertEquals(sizeExpected, sizeActual);
    }
}
