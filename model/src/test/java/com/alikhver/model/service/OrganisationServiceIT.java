package com.alikhver.model.service;


import com.alikhver.model.configuration.ModelConfigurationTest;
import com.alikhver.model.entity.Organisation;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ModelConfigurationTest.class,
        loader = AnnotationConfigContextLoader.class)
public class OrganisationServiceIT {
    @Autowired
    private OrganisationService organisationService;

    @Before
    public void setUp() {
        organisationService.deleteAll();

        User redactor = User.builder()
                .login("Login")
                .password("Password")
                .role(UserRole.REDACTOR)
                .build();
        Organisation organisation = Organisation.builder()
                .name("Name")
                .address("Address")
                .description("Description")
                .redactor(redactor)
                .dateCreated(new Date())
                .isActive(false)
                .build();

        organisationService.saveOrganisation(organisation);
    }

    @After
    public void tearDown() {
        organisationService.deleteAll();
    }

    @Test
    public void existsByIdWhenExistsTest() {
        //Given
        Organisation organisation = organisationService.getAll().get(0);
        Long correctId = organisation.getId();

        //When
        boolean actual = organisationService.existsById(correctId);

        //Then
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    public void existsByIdWhenDoesNotExistTest() {
        //Given
        Organisation organisation = organisationService.getAll().get(0);
        Long incorrectId = organisation.getId() + 1;

        //When
        boolean actual = organisationService.existsById(incorrectId);

        //Then
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    public void getAll() {
        //Given
        organisationService.deleteAll();

        User redactor = User.builder()
                .login("Login")
                .password("Password")
                .role(UserRole.REDACTOR)
                .build();
        Organisation organisation = Organisation.builder()
                .name("Name")
                .address("Address")
                .description("Description")
                .redactor(redactor)
                .dateCreated(new Date())
                .isActive(false)
                .build();

        User redactor1 = User.builder()
                .login("Login1")
                .password("Password1")
                .role(UserRole.REDACTOR)
                .build();
        Organisation organisation1 = Organisation.builder()
                .name("Name1")
                .address("Address1")
                .description("Description1")
                .redactor(redactor1)
                .dateCreated(new Date())
                .isActive(false)
                .build();

        User redactor2 = User.builder()
                .login("Login2")
                .password("Password2")
                .role(UserRole.REDACTOR)
                .build();
        Organisation organisation2 = Organisation.builder()
                .name("Name2")
                .address("Address2")
                .description("Description2")
                .redactor(redactor2)
                .dateCreated(new Date())
                .isActive(false)
                .build();

        organisationService.saveOrganisation(organisation);
        organisationService.saveOrganisation(organisation1);
        organisationService.saveOrganisation(organisation2);

        //When
        int sizeActual = organisationService.getAll().size();

        //Then
        int sizeExpected = 3;
        assertEquals(sizeExpected, sizeActual);
    }

    @Test
    @Transactional
    public void getAllWithPagination() {
        //Given
        Pageable pageable = PageRequest.of(0, 1);
        Organisation expected = organisationService.getAll().get(0);

        //When
        var pages = organisationService.getAll(pageable);
        Organisation actual = pages.getContent().get(0);

        //Then
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.isActive(), actual.isActive());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getRedactor().getId(), actual.getRedactor().getId());
        assertEquals(expected.getRedactor().getPassword(), actual.getRedactor().getPassword());
        assertEquals(expected.getRedactor().getRole(), actual.getRedactor().getRole());
        assertEquals(expected.getRedactor().getLogin(), actual.getRedactor().getLogin());
        assertEquals(expected.getUtilities(), actual.getUtilities());
        assertEquals(expected.getWorkers(), actual.getWorkers());
    }

    @Test
    public void existsOrganisationByNameWhenExists() {
        //Given
        Organisation organisation = organisationService.getAll().get(0);

        //When
        boolean existsActual = organisationService.existsOrganisationByName(organisation.getName());

        //Then
        boolean existsExpected = true;
        assertEquals(existsExpected, existsActual);
    }

    @Test
    public void existsOrganisationByNameWhenDoesNotExist() {
        //Given
        Organisation organisation = organisationService.getAll().get(0);

        //When
        boolean existsActual = organisationService
                .existsOrganisationByName(organisation.getName() + "Not Exists");

        //Then
        boolean existsExpected = false;
        assertEquals(existsExpected, existsActual);
    }

    @Test
    @Transactional
    public void saveOrganisation() {
        //Given
        organisationService.deleteAll();

        User redactor = User.builder()
                .login("Login")
                .password("Password")
                .role(UserRole.REDACTOR)
                .build();
        Organisation expected = Organisation.builder()
                .name("Name")
                .address("Address")
                .description("Description")
                .redactor(redactor)
                .dateCreated(new Date())
                .isActive(false)
                .build();


        //When
        organisationService.saveOrganisation(expected);

        //Then
        Long orgId = expected.getId();
        Optional<Organisation> optional = organisationService.getOrganisation(orgId);

        assertTrue(optional.isPresent());

        Organisation actual = optional.get();

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.isActive(), actual.isActive());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getRedactor().getId(), actual.getRedactor().getId());
        assertEquals(expected.getRedactor().getPassword(), actual.getRedactor().getPassword());
        assertEquals(expected.getRedactor().getRole(), actual.getRedactor().getRole());
        assertEquals(expected.getRedactor().getLogin(), actual.getRedactor().getLogin());
        assertEquals(expected.getUtilities(), actual.getUtilities());
        assertEquals(expected.getWorkers(), actual.getWorkers());
    }

    @Test
    public void deleteOrganisation() {
        //Given
        Organisation organisation = organisationService.getAll().get(0);

        //When
        organisationService.deleteOrganisation(organisation.getId());

        //Then
        boolean existsExpected = false;
        boolean existsActual = organisationService.existsById(organisation.getId());

        assertEquals(existsExpected, existsActual);
    }

    @Test
    public void deleteAll() {
        //Given
        int size = organisationService.getAll().size();
        assertNotEquals(0, size);
        //When
        organisationService.deleteAll();

        //Then
        int sizeExpected = 0;
        int sizeActual = organisationService.getAll().size();

        assertEquals(sizeExpected, sizeActual);
    }
}
