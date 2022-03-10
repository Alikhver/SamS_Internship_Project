package com.alikhver.web.facade;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.Utility;
import com.alikhver.model.service.OrganisationService;
import com.alikhver.model.service.UtilityService;
import com.alikhver.model.util.ValidationHelper;
import com.alikhver.web.converter.utility.UtilityConverter;
import com.alikhver.web.dto.utility.request.CreateUtilityRequest;
import com.alikhver.web.dto.utility.request.UpdateUtilityRequest;
import com.alikhver.web.dto.utility.response.CreateUtilityResponse;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import com.alikhver.web.exeption.organisation.NoOrganisationFoundException;
import com.alikhver.web.exeption.utility.NoUtilityFoundException;
import com.alikhver.web.exeption.utility.UtilityAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UtilityFacadeImpl implements UtilityFacade {
    private final UtilityService utilityService;
    private final OrganisationService organisationService;
    private final UtilityConverter utilityConverter;
    private final ValidationHelper validationHelper;

    @Override
    public GetUtilityResponse getUtility(Long id) {
        log.info("utilityFacade::getUtility -> start");

        validationHelper.validateForCorrectId(id, "UtilityId");

        Optional<Utility> optionalUtility = utilityService.getUtility(id);
        if (optionalUtility.isEmpty()) {
            log.warn("NoUtilityFoundException is thrown");
            throw new NoUtilityFoundException(
                    "No Utility with id = " + id + " found"
            );
        }
        Utility utility = optionalUtility.get();

        var response = utilityConverter.mapToGetUtilityResponse(utility);

        log.info("utilityFacade::getUtility -> done");
        return response;
    }

    @Override
    @Transactional
    public void updateUtility(Long id, UpdateUtilityRequest request) {
        log.info("utilityFacade::updateUtility -> start");

        validationHelper.validateForCorrectId(id, "UtilityId");

        Optional<Utility> optionalUtility = utilityService.getUtility(id);
        Utility utility;
        if (optionalUtility.isEmpty()) {
            log.warn("NoUtilityFoundException is thrown");
            throw new NoUtilityFoundException(
                    "No utility with id = " + id + " found"
            );
        } else {
            utility = optionalUtility.get();
        }

        Optional.ofNullable(request.getName()).ifPresent(utility::setName);
        Optional.ofNullable(request.getDescription()).ifPresent(utility::setDescription);
        Optional.ofNullable(request.getPrice()).ifPresent(utility::setPrice);

        utilityService.saveUtility(utility);
        log.info("utilityFacade::updateUtility -> done");
    }

    @Override
    @Transactional
    public void deleteUtility(Long id) {
        log.info("utilityFacade::deleteUtility -> start");

        validationHelper.validateForCorrectId(id, "UtilityId");

        if (!utilityService.existsUtility(id)) {
            log.warn("NoUtilityFoundException is thrown");
            throw new NoUtilityFoundException(
                    "No utility with id = " + id + " found"
            );
        } else {
            utilityService.deleteUtility(id);

            log.info("utilityFacade::deleteUtility -> done");
        }
    }

    @Override
    @Transactional
    public CreateUtilityResponse createUtility(CreateUtilityRequest request) {
        log.info("utilityFacade::createUtility -> start");

        Optional<Organisation> optionalOrganisation = organisationService.getOrganisation(
                request.getOrganisationId()
        );
        Organisation organisation;
        if (optionalOrganisation.isPresent()) {
            organisation = optionalOrganisation.get();
        } else {
            log.warn("NoOrganisationFoundException is thrown");
            throw new NoOrganisationFoundException(
                    "No Organisation with id = " + request.getOrganisationId() + " found"
            );
        }

        Utility utility = utilityConverter.mapToUtility(request);
        utility.setOrganisation(organisation);
        if (utilityService.existsUtility(utility)) {
            throw new UtilityAlreadyExistsException(
                    "Utility with name = '" + utility.getName() + "', price = '" + utility.getPrice() +
                            "', description = '" + utility.getDescription() + "' already exists"
            );
        }

        utilityService.saveUtility(utility);

        var response = utilityConverter.mapToCreateUtilityResponse(utility);

        log.info("utilityFacade::createUtility -> done");
        return response;
    }
}
