package com.alikhver.web.facade;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.Utility;
import com.alikhver.model.service.OrganisationService;
import com.alikhver.model.service.UtilityService;
import com.alikhver.web.converter.utility.UtilityConverter;
import com.alikhver.web.dto.utility.request.CreateUtilityRequest;
import com.alikhver.web.dto.utility.request.UpdateUtilityRequest;
import com.alikhver.web.dto.utility.response.CreateUtilityResponse;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import com.alikhver.web.exeption.organisation.NoOrganisationFoundException;
import com.alikhver.web.exeption.utility.NoUtilityFoundException;
import com.alikhver.web.exeption.utility.UtilityAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtilityFacadeImpl implements UtilityFacade {
    private final UtilityService utilityService;
    private final OrganisationService organisationService;
    private final UtilityConverter utilityConverter;

    @Override
    public GetUtilityResponse getUtility(Long id) {
        Optional<Utility> optionalUtility = utilityService.getUtility(id);
        if (optionalUtility.isEmpty()) {
            throw new NoUtilityFoundException(
                    "No Utility with id = " + id + " found"
            );
        }
        Utility utility = optionalUtility.get();
        return utilityConverter.mapToGetUtilityResponse(utility);
    }

    @Override
    @Transactional
    public void updateUtility(Long id, UpdateUtilityRequest request) {
        Optional<Utility> optionalUtility = utilityService.getUtility(id);
        Utility utility;
        if (optionalUtility.isEmpty()) {
            throw new NoUtilityFoundException(
                    "No utility with id = " + id + " found"
            );
        } else {
            utility = optionalUtility.get();
        }

        Optional.ofNullable(request.getName()).ifPresent(utility::setName);
        Optional.ofNullable(request.getDescription()).ifPresent(utility::setDescription);
        Optional.ofNullable(request.getPrice()).ifPresent(utility::setPrice);

        utilityService.save(utility);
    }

    @Override
    @Transactional
    public void deleteUtility(Long id) {
        if (!utilityService.utilityExists(id)) {
            throw new NoUtilityFoundException(
                    "No utility with id = " + id + " found"
            );
        } else {
            utilityService.deleteUtility(id);
        }
    }

    @Override
    @Transactional
    public CreateUtilityResponse createUtility(CreateUtilityRequest request) {
        Optional<Organisation> optionalOrganisation = organisationService.get(
                request.getOrganisationId()
        );
        Organisation organisation;
        if (optionalOrganisation.isPresent()) {
            organisation = optionalOrganisation.get();
        } else {
            throw new NoOrganisationFoundException(
                    "No Organisation with id = " + request.getOrganisationId() + " found"
            );
        }

        Utility utility = utilityConverter.mapToUtility(request);
        utility.setOrganisation(organisation);
        if (utilityService.utilityExists(utility)) {
            throw new UtilityAlreadyExistsException(
                    "Utility with name = '" + utility.getName() + "', price = '" + utility.getPrice() +
                            "', description = '" + utility.getDescription() + "' already exists"
            );
        }

        utilityService.save(utility);
        return utilityConverter.mapToCreateUtilityResponse(utility);
    }
}
