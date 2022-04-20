package com.alikhver.web.converter.organisation;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.UserRole;
import com.alikhver.web.WebApplication;
import com.alikhver.web.dto.organisation.request.CreateOrganisationRequest;
import com.alikhver.web.dto.organisation.response.GetOrganisationResponse;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class OrganisationConverterTests {
    private final static EasyRandom generator;

    static {
        EasyRandomParameters parameters = new EasyRandomParameters();
        parameters.collectionSizeRange(1, 1);
        generator = new EasyRandom(parameters);
    }

    @Autowired
    private OrganisationConverter organisationConverter;

    @Test
    public void mapToGetOrganisationResponseTest() {
        //Given

        Organisation organisation = generator.nextObject(Organisation.class);

        //When
        var actual = organisationConverter.mapToGetOrganisationResponse(organisation);

        //Then
        Assertions.assertEquals(actual.getId(), organisation.getId());
        Assertions.assertEquals(actual.getAddress(), organisation.getAddress());
        Assertions.assertEquals(actual.getDateCreated(), organisation.getDateCreated());
        Assertions.assertEquals(actual.getDescription(), organisation.getDescription());
        Assertions.assertEquals(actual.getName(), organisation.getName());
        Assertions.assertEquals(actual.getUtilities().get(0).getId(), organisation.getUtilities().get(0).getId());
        Assertions.assertEquals(actual.getWorkers().get(0).getId(), organisation.getWorkers().get(0).getId());
    }

    @Test
    public void mapToOrganisation() {
        //Given
        CreateOrganisationRequest request = generator.nextObject(CreateOrganisationRequest.class);

        //When
        var actual = organisationConverter.mapToOrganisation(request);

        // Then
        Assertions.assertEquals(request.getName(), actual.getName());
        Assertions.assertEquals(request.getAddress(), actual.getAddress());
        Assertions.assertEquals(request.getDescription(), actual.getDescription());
    }

    @Test
    public void mapToRedactorTest() {
        //Given
        CreateOrganisationRequest request = generator.nextObject(CreateOrganisationRequest.class);

        //When
        var actual = organisationConverter.mapToRedactor(request);

        //Then
        Assertions.assertEquals(actual.getLogin(), request.getRedactorLogin());
        Assertions.assertEquals(actual.getPassword(), request.getRedactorPassword());
        Assertions.assertEquals(actual.getRole(), UserRole.REDACTOR);
    }

    @Test
    public void mapToCreateOrganisationResponseTest() {
        //Given
        Organisation organisation = generator.nextObject(Organisation.class);

        //When
        var actual = organisationConverter.mapToCreateOrganisationResponse(organisation);

        //Then
        Assertions.assertEquals(actual.getId(), organisation.getId());
        Assertions.assertEquals(actual.getDescription(), organisation.getDescription());
        Assertions.assertEquals(actual.getName(), organisation.getName());
    }

    @Test
    public void mapToPageOfGetOrganisationResponse() {
        //Given
        PageImpl<Organisation> page = new PageImpl<>(List.of(generator.nextObject(Organisation.class)),
                PageRequest.of(0, 1),
                1);
        List<Organisation> pageElements = page.getContent();

        //When
        Page<GetOrganisationResponse> actual = organisationConverter.mapToPageOfGetOrganisationResponse(page);
        List<GetOrganisationResponse> actualElements = actual.getContent();

        //Then
        Assertions.assertEquals(pageElements.get(0).getId(), actualElements.get(0).getId());
        Assertions.assertEquals(pageElements.get(0).getAddress(), actualElements.get(0).getAddress());
        Assertions.assertEquals(pageElements.get(0).getName(), actualElements.get(0).getName());
        Assertions.assertEquals(pageElements.get(0).getDateCreated(), actualElements.get(0).getDateCreated());
        Assertions.assertEquals(pageElements.get(0).getDescription(), actualElements.get(0).getDescription());
        Assertions.assertEquals(pageElements.get(0).getUtilities().get(0).getId(), actualElements.get(0).getUtilities().get(0).getId());
        Assertions.assertEquals(pageElements.get(0).getWorkers().get(0).getId(), actualElements.get(0).getWorkers().get(0).getId());
        Assertions.assertEquals(pageElements.get(0).getUtilities().get(0).getName(), actualElements.get(0).getUtilities().get(0).getName());
    }

}
