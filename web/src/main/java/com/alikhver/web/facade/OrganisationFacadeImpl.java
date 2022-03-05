package com.alikhver.web.facade;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.User;
import com.alikhver.model.service.OrganisationService;
import com.alikhver.model.service.UserService;
import com.alikhver.web.converter.OrganisationConverter;
import com.alikhver.web.dto.organisation.request.CreateOrganisationRequest;
import com.alikhver.web.dto.organisation.request.UpdateOrganisationRequest;
import com.alikhver.web.dto.organisation.response.CreateOrganisationResponse;
import com.alikhver.web.dto.organisation.response.GetOrganisationResponse;
import com.alikhver.web.exeption.organisation.NoOrganisationFoundException;
import com.alikhver.web.exeption.organisation.OrganisationAlreadyExistsException;
import com.alikhver.web.exeption.organisation.OrganisationIsAlreadyLaunchedException;
import com.alikhver.web.exeption.organisation.OrganisationIsAlreadySuspendedException;
import com.alikhver.web.exeption.user.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganisationFacadeImpl implements OrganisationFacade {
    private final OrganisationService organisationService;
    private final UserService userService;
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
        if (organisationService.organisationExistsByName(request.getName())) {
            throw new OrganisationAlreadyExistsException(
              "Organisation with name = " + request.getName() + " already exists"
            );
        }

        if (userService.userExistsByLogin(request.getRedactorLogin())) {
            throw new UserAlreadyExistsException(
                    "User with login = " + request.getRedactorLogin() + " already exists"
            );
        }
        User redactor = organisationConverter.mapToRedactor(request);
        userService.createUser(redactor);

        Organisation organisation = organisationConverter.mapToOrganisation(request);

        organisation.setRedactor(redactor);

        organisation = organisationService.createOrganisation(organisation);

        return organisationConverter.mapToCreateOrganisationResponse(organisation);
    }

    @Override
    public List<GetOrganisationResponse> getOrganisations() {
        List<Organisation> organisations = organisationService.getAllOrganisations();
        return organisationConverter.mapToListOfGetOrganisationResponse(organisations);
    }

    @Override
    public void updateOrganisation(Long id, UpdateOrganisationRequest request) {
        Optional<Organisation> optionalOrganisation = organisationService.getOrganisation(id);
        Organisation organisation;
        if (optionalOrganisation.isPresent()) {
            organisation = optionalOrganisation.get();
        } else {
            throw new NoOrganisationFoundException(
                    "No Organisation with id = " + id + " found"
            );
        }
        Optional.ofNullable(request.getName()).ifPresent(organisation::setName);
        Optional.ofNullable(request.getDescription()).ifPresent(organisation::setDescription);
        Optional.ofNullable(request.getAddress()).ifPresent(organisation::setAddress);
        organisationService.updateOrganisation(organisation);
    }

    @Override
    public void deleteOrganisation(Long id) {
        if (organisationService.organisationExistsById(id)) {
            organisationService.deleteOrganisation(id);
        } else {
            throw new NoOrganisationFoundException(
              "No Organisation with id = " + id + " found"
            );
        }
    }

    @Override
    @Transactional
    public void suspendOrganisation(Long id) {
        Optional<Organisation> optionalOrganisation = organisationService.getOrganisation(id);
        Organisation organisation;
        if (optionalOrganisation.isEmpty()) {
            throw new NoOrganisationFoundException(
                    "No organisation with id = " + id + " found"
            );
        } else {
            organisation = optionalOrganisation.get();
        }

        if (organisation.isActive()) {
            organisation.setActive(false);
            organisationService.updateOrganisation(organisation);
        } else {
            throw new OrganisationIsAlreadySuspendedException(
                    "Organisation with id = " + id + " is already suspended"
            );
        }
    }

    @Override
    @Transactional
    public void launchOrganisation(Long id) {
        Optional<Organisation> optionalOrganisation = organisationService.getOrganisation(id);
        Organisation organisation;
        if (optionalOrganisation.isEmpty()) {
            throw new NoOrganisationFoundException(
                    "No organisation with id = " + id + " found"
            );
        } else {
            organisation = optionalOrganisation.get();
        }

        if (!organisation.isActive()) {
            organisation.setActive(true);
            organisationService.updateOrganisation(organisation);
        } else {
            throw new OrganisationIsAlreadyLaunchedException(
                    "Organisation with id = " + id + " is already running"
            );
        }
    }
}
