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

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ModelConfigurationTest.class,
        loader = AnnotationConfigContextLoader.class)
public class UtilityServiceIT {

    @Autowired
    private UtilityService utilityService;

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private WorkerService workerService;

    private Organisation organisation;

    @Before
    public void setUp() {
        utilityService.deleteAll();

        if (organisationService.getAll().size() == 0) {
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

            organisation = org;
        } else {
            organisation = organisationService.getAll().get(0);
        }

        Utility utility = Utility.builder()
                .name("Utility")
                .description("Description")
                .organisation(organisation)
                .price(1.65D)
                .build();

        utilityService.saveUtility(utility);
    }

    @After
    public void tearDown() {
        utilityService.deleteAll();
    }

    @Test
    @Transactional
    public void saveUtilityTest() {
        //Given
        Utility expected = Utility.builder()
                .name("Utility1")
                .description("Description1")
                .organisation(organisation)
                .price(1.65D)
                .build();

        //When
        utilityService.saveUtility(expected);

        //Then
        Utility actual = utilityService.getUtility(expected.getId()).get();

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getWorkers(), actual.getWorkers());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getOrganisation().getId(), actual.getOrganisation().getId());
    }

    @Test
    @Transactional
    public void existsUtilityByUtilityWhenExists() {
        //Given
        Utility utility = utilityService.getUtilitiesOfOrganisation(organisation.getId()).get(0);

        //When
        boolean existsActual = utilityService.existsUtility(utility);

        //Then
        boolean existsExpected = true;

        assertEquals(existsExpected, existsActual);
    }

    @Test
    @Transactional
    public void existsUtilityByUtilityWhenNotExist() {
        //Given
        Utility utility = utilityService.getUtilitiesOfOrganisation(organisation.getId()).get(0);

        String wrongName = utility.getName() + "Wrong Name";
        String wrongDescription = utility.getDescription() + "Wrong Desc";
        Double wrongPrice = utility.getPrice() + 1;

        Utility wrongUtility = Utility.builder()
                .id(utility.getId() + 1)
                .name(wrongName)
                .description(wrongDescription)
                .organisation(organisation)
                .price(wrongPrice)
                .build();
        //When
        boolean existsActual = utilityService.existsUtility(wrongUtility);

        //Then
        boolean existsExpected = false;

        assertEquals(existsExpected, existsActual);
    }

    @Test
    @Transactional
    public void existsUtilityByIdWhenExists() {
        //Given
        Utility utility = utilityService.getUtilitiesOfOrganisation(organisation.getId()).get(0);

        //When
        boolean existsActual = utilityService.existsUtility(utility.getId());

        //Then
        boolean existsExpected = true;

        assertEquals(existsExpected, existsActual);
    }

    @Test
    @Transactional
    public void existsUtilityByIdWhenNotExists() {
        //Given
        Utility utility = utilityService.getUtilitiesOfOrganisation(organisation.getId()).get(0);

        //When
        boolean existsActual = utilityService.existsUtility(utility.getId() + 1);

        //Then
        boolean existsExpected = false;

        assertEquals(existsExpected, existsActual);
    }

    @Test
    @Transactional
    public void getUtilityTest() {
        //Given
        Utility expected = utilityService.getUtilitiesOfOrganisation(organisation.getId()).get(0);

        //When
        Utility actual = utilityService.getUtility(expected.getId()).get();

        //Then
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    @Transactional
    public void getUtilityWhenNotExists() {
        //Given
        Utility expected = utilityService.getUtilitiesOfOrganisation(organisation.getId()).get(0);

        //When
        Optional<Utility> actual = utilityService.getUtility(expected.getId() + 1);

        //Then
        assertFalse(actual.isPresent());
    }

    @Test
    @Transactional
    public void getAllUtilitiesOfOrganisationWithPagination() {
        //Given
        utilityService.deleteAll();

        Utility utility1 = Utility.builder()
                .name("Utility1")
                .description("Description1")
                .organisation(organisation)
                .price(1.651D)
                .build();

        Utility utility2 = Utility.builder()
                .name("Utility2")
                .description("Description2")
                .organisation(organisation)
                .price(1.652D)
                .build();

        utilityService.saveUtility(utility1);
        utilityService.saveUtility(utility2);

        Pageable pageable = PageRequest.of(0, 5);

        //When
        int sizeActual = utilityService.getAllUtilitiesOfOrganisation(organisation.getId(), pageable).getContent().size();

        //Then
        int sizeExpected = 2;

        assertEquals(sizeExpected, sizeActual);
    }

    @Test
    @Transactional
    public void getAllUtilitiesOfOrganisation() {
        //Given
        utilityService.deleteAll();

        Utility utility1 = Utility.builder()
                .name("Utility1")
                .description("Description1")
                .organisation(organisation)
                .price(1.651D)
                .build();

        Utility utility2 = Utility.builder()
                .name("Utility2")
                .description("Description2")
                .organisation(organisation)
                .price(1.652D)
                .build();

        utilityService.saveUtility(utility1);
        utilityService.saveUtility(utility2);

        //When
        int sizeActual = utilityService.getUtilitiesOfOrganisation(organisation.getId()).size();

        //Then
        int sizeExpected = 2;

        assertEquals(sizeExpected, sizeActual);
    }

    @Test
    @Transactional
    public void deleteUtilityTest() {
        //Given
        Utility utility = utilityService.getUtilitiesOfOrganisation(organisation.getId()).get(0);

        //When
        utilityService.deleteUtility(utility.getId());

        //Then
        assertFalse(utilityService.existsUtility(utility));
    }

    @Test
    @Transactional
    public void utilityAlreadyHasWorkerWhenHasWorkerTest() {
        //Given
        Utility utility = utilityService.getUtilitiesOfOrganisation(organisation.getId()).get(0);

        Worker worker = Worker.builder()
                .firstName("FirstName")
                .lastName("LastName")
                .description("Desc")
                .organisation(organisation)
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
        boolean hasWorkerActual = utilityService.utilityAlreadyHasWorker(utility.getId(), worker.getId());

        //Then
        boolean hasUtilityExpected = true;

        assertEquals(hasUtilityExpected, hasWorkerActual);

        workerService.deleteWorker(worker.getId());
    }

    @Test
    @Transactional
    public void deleteAllTest() {
        //Given
        int size = utilityService.getUtilitiesOfOrganisation(organisation.getId()).size();
        assertNotEquals(0, size);

        //When
        utilityService.deleteAllUtilitiesOfOrganisation(organisation.getId());

        //Then
        int sizeExpected = 0;
        int sizeActual = utilityService.getUtilitiesOfOrganisation(organisation.getId()).size();

        assertEquals(sizeExpected, sizeActual);
    }
}
