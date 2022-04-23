package com.alikhver.model.service;


import com.alikhver.model.configuration.ModelConfigurationTest;
import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.User;
import com.alikhver.model.entity.UserRole;
import com.alikhver.model.entity.Utility;
import com.alikhver.model.entity.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ModelConfigurationTest.class,
        loader = AnnotationConfigContextLoader.class)
public class WorkerServiceIT {

    @Autowired
    private WorkerService workerService;

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private UtilityService utilityService;

    private Organisation organisation;

    @Before
    public void setUp() {
        organisationService.deleteAll();

        User redactor = User.builder()
                .login("Login")
                .password("Password")
                .role(UserRole.REDACTOR)
                .build();

        Organisation org = Organisation.builder()
                .name("Name")
                .address("Address")
                .description("Description")
                .redactor(redactor)
                .dateCreated(new Date())
                .isActive(false)
                .build();

        organisationService.saveOrganisation(org);

        organisation = organisationService.getAll().get(0);

        Worker worker = Worker.builder()
                .firstName("FirstName")
                .lastName("LastName")
                .description("Desc")
                .organisation(organisation)
                .build();

        workerService.saveWorker(worker);
    }

    @After
    public void tearDown() {
        workerService.deleteWorkersOfOrganisation(organisation.getId());
        organisationService.deleteAll();
    }

    @Test
    public void getWorkerWhenExistsTest() {
        //Given
        Worker expected = workerService.findAllWorkersOfOrganisation(organisation.getId()).get(0);

        //When
        Optional<Worker> optionalWorker = workerService.getWorker(expected.getId());

        //Then
        assertTrue(optionalWorker.isPresent());

        Worker actual = optionalWorker.get();

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getOrganisation().getId(), actual.getOrganisation().getId());
    }

    @Test
    public void getWorkerWhenNotExistsTest() {
        //Given
        Worker expected = workerService.findAllWorkersOfOrganisation(organisation.getId()).get(0);

        //When
        Optional<Worker> actual = workerService.getWorker(expected.getId() + 1);

        //Then
        assertFalse(actual.isPresent());
    }

    @Test
    public void saveWorker() {
        //Given
        workerService.deleteWorkersOfOrganisation(organisation.getId());

        Worker worker = Worker.builder()
                .firstName("FirstName")
                .lastName("LastName")
                .description("Desc")
                .organisation(organisation)
                .build();

        //When
        workerService.saveWorker(worker);

        //Then
        boolean existsActual = workerService.existsWorker(worker.getId());
        assertTrue(existsActual);
    }

    @Test
    public void existsWorkerWhenExistsTest() {
        //Given
        Worker expected = workerService.findAllWorkersOfOrganisation(organisation.getId()).get(0);

        //When
        boolean existsActual = workerService.existsWorker(expected.getId());

        //Then
        boolean existsExpected = true;

        assertEquals(existsExpected, existsActual);
    }

    @Test
    public void existsWorkerWhenNotExistsTest() {
        //Given
        Worker expected = workerService.findAllWorkersOfOrganisation(organisation.getId()).get(0);

        //When
        boolean existsActual = workerService.existsWorker(expected.getId() + 1);

        //Then
        boolean existsExpected = false;

        assertEquals(existsExpected, existsActual);
    }

    @Test
    public void deleteWorkerTest() {
        //Given
        Worker worker = workerService.findAllWorkersOfOrganisation(organisation.getId()).get(0);

        //When
        workerService.deleteWorker(worker.getId());

        //Then
        boolean existsActual = workerService.existsWorker(worker.getId());
        boolean existsExpected = false;

        assertEquals(existsExpected, existsActual);
    }

    @Test
    public void findAllWorkersOfOrganisation() {
        //Given
        workerService.deleteWorkersOfOrganisation(organisation.getId());

        Worker worker1 = Worker.builder()
                .firstName("FirstName1")
                .lastName("LastName1")
                .description("Desc1")
                .organisation(organisation)
                .build();

        workerService.saveWorker(worker1);

        Worker worker2 = Worker.builder()
                .firstName("FirstName2")
                .lastName("LastName2")
                .description("Desc2")
                .organisation(organisation)
                .build();

        workerService.saveWorker(worker2);

        //When
        int sizeActual = workerService.findAllWorkersOfOrganisation(organisation.getId()).size();

        //Then
        int sizeExpected = 2;

        assertEquals(sizeExpected, sizeActual);
    }

    @Test
    public void findAllWorkersOfOrganisationWithPagination() {
        //Given
        workerService.deleteWorkersOfOrganisation(organisation.getId());

        Worker worker1 = Worker.builder()
                .firstName("FirstName1")
                .lastName("LastName1")
                .description("Desc1")
                .organisation(organisation)
                .build();

        workerService.saveWorker(worker1);

        Worker worker2 = Worker.builder()
                .firstName("FirstName2")
                .lastName("LastName2")
                .description("Desc2")
                .organisation(organisation)
                .build();

        workerService.saveWorker(worker2);

        Pageable pageable = PageRequest.of(0, 5);

        //When
        int sizeActual = workerService.
                findAllWorkersOfOrganisation(organisation.getId(), pageable)
                .getContent()
                .size();

        //Then
        int sizeExpected = 2;

        assertEquals(sizeExpected, sizeActual);
    }

    @Test
    @Transactional
    public void workerAlreadyHasUtilityWhenHasUtilityTest() {
        //Given
        Worker worker = workerService.findAllWorkersOfOrganisation(organisation.getId()).get(0);

        Utility utility = Utility.builder()
                .name("Utility")
                .description("Description")
                .organisation(organisation)
                .price(1.65D)
                .build();

        List<Utility> utilities = new ArrayList<>();
        utilities.add(utility);

        List<Worker> workers = new ArrayList<>();
        workers.add(worker);

        utility.setWorkers(workers);
        worker.setUtilities(utilities);


        workerService.saveWorker(worker);
        utilityService.saveUtility(utility);

        //When
        boolean hasUtilityActual = workerService.workerAlreadyHasUtility(worker.getId(), utility.getId());

        //Then
        boolean hasUtilityExpected = true;

        assertEquals(hasUtilityExpected, hasUtilityActual);

        utilityService.deleteUtility(utility.getId());
    }

    @Test
    @Transactional
    public void workerAlreadyHasUtilityWhenDoesntHaveUtilityTest() {
        //Given
        Worker worker = workerService.findAllWorkersOfOrganisation(organisation.getId()).get(0);

        Utility utility = Utility.builder()
                .name("Utility")
                .description("Description")
                .organisation(organisation)
                .price(1.65D)
                .build();

        utilityService.saveUtility(utility);

        //When
        boolean hasUtilityActual = workerService.workerAlreadyHasUtility(worker.getId(), utility.getId());

        //Then
        boolean hasUtilityExpected = false;

        assertEquals(hasUtilityExpected, hasUtilityActual);
        utilityService.deleteAllUtilitiesOfOrganisation(organisation.getId());
    }

    @Test
    public void deleteWorkersOfOrganisationTest() {
        //Given
        int size = workerService.findAllWorkersOfOrganisation(organisation.getId()).size();

        assertNotEquals(0, size);

        //When
        workerService.deleteWorkersOfOrganisation(organisation.getId());

        //Then
        int sizeExpected = 0;
        int sizeActual = workerService.findAllWorkersOfOrganisation(organisation.getId()).size();

        assertEquals(sizeExpected, sizeActual);

    }

    @Test
    @Transactional
    public void getWorkersByUtilityId() {//Given
        Worker expected = workerService.findAllWorkersOfOrganisation(organisation.getId()).get(0);

        Utility utility = Utility.builder()
                .name("Utility")
                .description("Description")
                .organisation(organisation)
                .price(1.65D)
                .build();

        List<Utility> utilities = new ArrayList<>();
        utilities.add(utility);

        List<Worker> workers = new ArrayList<>();
        workers.add(expected);

        utility.setWorkers(workers);
        expected.setUtilities(utilities);

        workerService.saveWorker(expected);
        utilityService.saveUtility(utility);

        Pageable pageable = PageRequest.of(0, 5);

        //When
        Page<Worker> pages = workerService.getWorkersByUtilityId(utility.getId(), pageable);
        Worker actual = pages.getContent().get(0);

        //Then

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getOrganisation().getId(), actual.getOrganisation().getId());
    }
}
