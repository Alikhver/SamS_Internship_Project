package com.alikhver.web.facade;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.User;
import com.alikhver.model.service.OrganisationService;
import com.alikhver.web.converter.OrganisationConverter;
import com.alikhver.web.dto.organisation.request.CreateOrganisationRequest;
import com.alikhver.web.dto.organisation.response.CreateOrganisationResponse;
import com.alikhver.web.dto.organisation.response.GetOrganisationResponse;
import com.alikhver.web.exeption.organisation.NoOrganisationFoundException;
import com.alikhver.web.exeption.organisation.OrganisationAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganisationFacadeImpl implements OrganisationFacade {
    private final OrganisationService organisationService;
    private final OrganisationConverter organisationConverter;

    @Override
    public GetOrganisationResponse getOrganisation(Long id) throws NoOrganisationFoundException {
        Optional<Organisation> optionalOrganisation = organisationService.getOrganisation(id);
        if (optionalOrganisation.isPresent()) {
            Organisation organisation = optionalOrganisation.get();
            return organisationConverter.mapToGetOrganisationResponse(organisation);
        } {
            throw new NoOrganisationFoundException(
                    "No Organisation with id = " + id + " found"
            );
        }
    }

    @Override
    @Transactional
    public CreateOrganisationResponse createOrganisation(CreateOrganisationRequest request) {
        if (!organisationService.organisationExistsByName(request.getName())) {
            throw new OrganisationAlreadyExistsException(
              "Organisation with name = " + request.getName() + " already exists"
            );
        }
        Organisation organisation = organisationConverter.mapToOrganisation(request);
        User redactor = organisationConverter.mapToRedactor(request);
        organisation.setRedactor(redactor);

        organisation = organisationService.createOrganisation(organisation);

        return organisationConverter.mapToCreateOrganisationResponse(organisation);
    }

    @Override
    public List<GetOrganisationResponse> getOrganisations() {
        List<Organisation> organisations = organisationService.getOrganisations();
        return organisationConverter.mapToListOfGetOrganisationResponse(organisations);
    }
}
