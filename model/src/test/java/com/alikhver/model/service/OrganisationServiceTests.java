package com.alikhver.model.service;


import com.alikhver.model.configuration.ModelConfigurationTest;
import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.User;
import com.alikhver.model.entity.UserRole;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ModelConfigurationTest.class,
        loader = AnnotationConfigContextLoader.class)
public class OrganisationServiceTests {
    @Autowired
    private OrganisationService organisationService;

    private static final EasyRandom generator;

    static {
        EasyRandomParameters parameters = new EasyRandomParameters();
        parameters.collectionSizeRange(1, 1);
        generator = new EasyRandom(parameters);
    }

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


}
