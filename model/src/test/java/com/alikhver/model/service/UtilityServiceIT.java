package com.alikhver.model.service;

import com.alikhver.model.configuration.ModelConfigurationTest;
import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.User;
import com.alikhver.model.entity.UserRole;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Date;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ModelConfigurationTest.class,
        loader = AnnotationConfigContextLoader.class)
public class UtilityServiceIT {

    @Autowired
    private UtilityService utilityService;

    @Autowired
    private OrganisationService organisationService;

    @Before
    public void setUp() {
        utilityService.deleteAll();

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

    }
}
