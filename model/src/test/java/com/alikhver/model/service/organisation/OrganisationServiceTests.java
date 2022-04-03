package com.alikhver.model.service.organisation;


import com.alikhver.model.configuration.ModelConfigurationTest;
import com.alikhver.model.service.OrganisationService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(classes = ModelConfigurationTest.class,
        loader = AnnotationConfigContextLoader.class)
public class OrganisationServiceTests {
    @Autowired
    private OrganisationService organisationService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void existsByIdWhenCorrectIdTest() {

    }

    @Test
    public void whenIncorrectId_existsById_Test() {
        //Given


        //When


        //Then
    }
}
