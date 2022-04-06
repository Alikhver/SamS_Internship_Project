package com.alikhver.model.service;

import com.alikhver.model.configuration.ModelConfigurationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ModelConfigurationTest.class,
        loader = AnnotationConfigContextLoader.class)
public class ScheduleRecordIT {
    @After
    public void tearDown() throws Exception {

    }

    @Before
    public void setUp() throws Exception {

    }
}
