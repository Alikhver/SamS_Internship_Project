package com.alikhver.web.facade;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.Utility;
import com.alikhver.model.service.OrganisationService;
import com.alikhver.model.service.UtilityService;
import com.alikhver.model.service.WorkerService;
import com.alikhver.model.util.ValidationHelper;
import com.alikhver.web.converter.utility.UtilityConverter;
import com.alikhver.web.converter.worker.WorkerConverter;
import com.alikhver.web.dto.utility.request.CreateUtilityRequest;
import com.alikhver.web.dto.utility.request.UpdateUtilityRequest;
import com.alikhver.web.dto.utility.response.CreateUtilityResponse;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import com.alikhver.web.exception.organisation.NoOrganisationFoundException;
import com.alikhver.web.exception.utility.NoUtilityFoundException;
import com.alikhver.web.exception.utility.UtilityAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UtilityFacadeImpl implements UtilityFacade {
    private final UtilityService utilityService;
    private final OrganisationService organisationService;
    private final UtilityConverter utilityConverter;
    private final ValidationHelper validationHelper;
    private final WorkerService workerService;
    private final WorkerConverter workerConverter;

    @Override
    public GetUtilityResponse getUtility(Long id) {
        log.info("getUtility -> start");

        validationHelper.validateForCorrectId(id, "UtilityId");

        Optional<Utility> optionalUtility = utilityService.getUtility(id);
        if (optionalUtility.isEmpty()) {
            log.warn("NoUtilityFoundException is thrown");
            throw new NoUtilityFoundException(id);
        }
        Utility utility = optionalUtility.get();

        var response = utilityConverter.mapToGetUtilityResponse(utility);

        log.info("getUtility -> done");
        return response;
    }

    @Override
    @Transactional
    public void updateUtility(Long id, UpdateUtilityRequest request) {
        log.info("updateUtility -> start");

        validationHelper.validateForCorrectId(id, "UtilityId");

        Optional<Utility> optionalUtility = utilityService.getUtility(id);
        Utility utility;
        if (optionalUtility.isEmpty()) {
            log.warn("NoUtilityFoundException is thrown");
            throw new NoUtilityFoundException(id);
        } else {
            utility = optionalUtility.get();
        }

        Optional.ofNullable(request.getName()).ifPresent(utility::setName);
        Optional.ofNullable(request.getDescription()).ifPresent(utility::setDescription);
        Optional.ofNullable(request.getPrice()).ifPresent(utility::setPrice);

        utilityService.saveUtility(utility);
        log.info("updateUtility -> done");
    }

    @Override
    @Transactional
    public void deleteUtility(Long id) {
        log.info("deleteUtility -> start");

        validationHelper.validateForCorrectId(id, "UtilityId");

        if (!utilityService.existsUtility(id)) {
            log.warn("NoUtilityFoundException is thrown");
            throw new NoUtilityFoundException(id);
        } else {
            utilityService.deleteUtility(id);

            log.info("deleteUtility -> done");
        }
    }

    @Override
    public Page<GetWorkerResponse> getWorkersOfUtility(Long utilityId, int page, int size) {
        log.info("getWorkersOfUtility -> start");

        validationHelper.validateForCorrectId(utilityId, "WorkerId");
        if (!utilityService.existsUtility(utilityId)) {
            throw new NoUtilityFoundException(utilityId);
        }
        Pageable pageable = PageRequest.of(page, size);
        var workers = workerService.getWorkersByUtilityId(utilityId, pageable);

        var response = workerConverter.mapToPageOfGetWorkerResponse(workers);

        log.info("getWorkersOfUtility -> done");
        return response;
    }

    @Override
    public List<GetWorkerResponse> getWorkersOfUtility(Long utilityId) {
        log.info("getWorkersOfUtility -> start");

        validationHelper.validateForCorrectId(utilityId, "WorkerId");
        if (!utilityService.existsUtility(utilityId)) {
            throw new NoUtilityFoundException(utilityId);
        }
        var workers = workerService.getWorkersByUtilityId(utilityId);

        var response = workerConverter.mapToListOfGetWorkerResponse(workers);

        log.info("getWorkersOfUtility -> done");
        return response;
    }

    @Override
    @Transactional
    public CreateUtilityResponse createUtility(CreateUtilityRequest request) {
        log.info("createUtility -> start");

        Optional<Organisation> optionalOrganisation = organisationService.getOrganisation(
                request.getOrganisationId()
        );
        Organisation organisation;
        if (optionalOrganisation.isPresent()) {
            organisation = optionalOrganisation.get();
        } else {
            log.warn("NoOrganisationFoundException is thrown");
            throw new NoOrganisationFoundException(request.getOrganisationId());
        }

        Utility utility = utilityConverter.mapToUtility(request);
        utility.setOrganisation(organisation);
        if (utilityService.existsUtility(utility)) {
            throw new UtilityAlreadyExistsException();
        }

        utilityService.saveUtility(utility);

        var response = utilityConverter.mapToCreateUtilityResponse(utility);

        log.info("createUtility -> done");
        return response;
    }
}
