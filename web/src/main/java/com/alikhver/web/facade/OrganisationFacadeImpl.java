package com.alikhver.web.facade;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.User;
import com.alikhver.model.entity.Utility;
import com.alikhver.model.entity.Worker;
import com.alikhver.model.service.OrganisationService;
import com.alikhver.model.service.ScheduleRecordService;
import com.alikhver.model.service.UserService;
import com.alikhver.model.service.UtilityService;
import com.alikhver.model.service.WorkerService;
import com.alikhver.model.util.ValidationHelper;
import com.alikhver.web.converter.organisation.OrganisationConverter;
import com.alikhver.web.converter.utility.UtilityConverter;
import com.alikhver.web.converter.worker.WorkerConverter;
import com.alikhver.web.dto.organisation.request.CreateOrganisationRequest;
import com.alikhver.web.dto.organisation.request.UpdateOrganisationRequest;
import com.alikhver.web.dto.organisation.response.CreateOrganisationResponse;
import com.alikhver.web.dto.organisation.response.GetOrganisationResponse;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import com.alikhver.web.exception.organisation.NoOrganisationFoundException;
import com.alikhver.web.exception.organisation.OrganisationAlreadyExistsException;
import com.alikhver.web.exception.organisation.OrganisationIsAlreadyLaunchedException;
import com.alikhver.web.exception.organisation.OrganisationIsAlreadySuspendedException;
import com.alikhver.web.exception.user.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganisationFacadeImpl implements OrganisationFacade {
    private final OrganisationService organisationService;
    private final UserService userService;
    private final WorkerService workerService;
    private final UtilityService utilityService;
    private final ScheduleRecordService scheduleRecordService;
    private final ScheduleRecordFacade scheduleRecordFacade;
    private final OrganisationConverter organisationConverter;
    private final WorkerConverter workerConverter;
    private final UtilityConverter utilityConverter;
    private final ValidationHelper validationHelper;

    @Override
    public GetOrganisationResponse getOrganisation(Long id) throws NoOrganisationFoundException {
        log.info("getOrganisation -> start");

        validationHelper.validateForCorrectId(id, "OrganisationId");

        Optional<Organisation> optionalOrganisation = organisationService.getOrganisation(id);
        if (optionalOrganisation.isPresent()) {
            Organisation organisation = optionalOrganisation.get();
            var result = organisationConverter.mapToGetOrganisationResponse(organisation);

            log.info("getOrganisation -> done");
            return result;
        } {
            log.warn("NoOrganisationFoundException is thrown");
            throw new NoOrganisationFoundException(
                    "No Organisation with id = " + id + " found"
            );
        }
    }

    @Override
    @Transactional
    public CreateOrganisationResponse createOrganisation(CreateOrganisationRequest request) {
        log.info("createOrganisation -> start");

        if (organisationService.existsOrganisationByName(request.getName())) {
            log.warn("OrganisationAlreadyExistsException is thrown");
            throw new OrganisationAlreadyExistsException(
              "Organisation with name = " + request.getName() + " already exists"
            );
        }

        if (userService.existsUserByLogin(request.getRedactorLogin())) {
            log.warn("UserAlreadyException is thrown");
            throw new UserAlreadyExistsException(
                    "User with login = " + request.getRedactorLogin() + " already exists"
            );
        }
        User redactor = organisationConverter.mapToRedactor(request);

        Organisation organisation = organisationConverter.mapToOrganisation(request);
        organisation.setRedactor(redactor);

        organisation = organisationService.saveOrganisation(organisation);

        var response = organisationConverter.mapToCreateOrganisationResponse(organisation);

        log.info("createOrganisation -> done");
        return response;
    }

    @Override
    public Page<GetOrganisationResponse> getOrganisations(int page, int size) {
        log.info("getOrganisations -> start");

        validatePageAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size);
        Page<Organisation> organisations = organisationService.getAll(pageable);

        var response = organisationConverter.mapToPageOfGetOrganisationResponse(organisations);

        log.info("getOrganisations -> done");
        return response;
    }

    @Override
    @Transactional
    public Page<GetWorkerResponse> getWorkers(Long organisationId, int page, int size) {
        log.info("getWorkers -> start");

        validatePageAndSize(page, size);

        if (!organisationService.existsById(organisationId)) {
            log.warn("NoOrganisationFoundException is thrown");
            throw new NoOrganisationFoundException(
              "No Organisation with id = " + organisationId + "found"
            );
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Worker> workers = workerService.findAllWorkersOfOrganisation(organisationId, pageable);

        var response = workerConverter.mapToPageOfGetWorkerResponse(workers);

        log.info("getWorkers -> done");
        return response;
    }



    @Override
    @Transactional(readOnly = true)
    public Page<GetUtilityResponse> getUtilities(Long organisationId, int page, int size) {
        log.info("getUtilities -> start");

        validatePageAndSize(page, size);

        if (!organisationService.existsById(organisationId)) {
            log.warn("NoOrganisationFoundException is thrown");
            throw new NoOrganisationFoundException(
                    "No Organisation with id = " + organisationId + "found"
            );
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Utility> utilities = utilityService.getAllUtilitiesOfOrganisation(organisationId, pageable);

        var response = utilityConverter.mapToPageOfGetUtilityResponse(utilities);

        log.info("getUtilities -> done");
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetWorkerResponse> getWorkers(Long orgId) {
        log.info("getWorkers -> start");

        if (!organisationService.existsById(orgId)) {
            log.warn("NoOrganisationFoundException is thrown");
            throw new NoOrganisationFoundException(
                    "No Organisation with id = " + orgId + "found"
            );
        }

        List<Worker> workers = workerService.findAllWorkersOfOrganisation(orgId);

        var response = workerConverter.mapToListOfGetWorkerResponse(workers);

        log.info("getWorkers -> done");
        return response;
    }

    @Override
    public void updateOrganisation(Long id, UpdateOrganisationRequest request) {
        log.info("updateOrganisation -> start");

        Optional<Organisation> optionalOrganisation = organisationService.getOrganisation(id);
        Organisation organisation;
        if (optionalOrganisation.isPresent()) {
            organisation = optionalOrganisation.get();
        } else {
            log.warn("NoOrganisationFoundException is thrown");
            throw new NoOrganisationFoundException(
                    "No Organisation with id = " + id + " found"
            );
        }
        Optional.ofNullable(request.getName()).ifPresent(organisation::setName);
        Optional.ofNullable(request.getDescription()).ifPresent(organisation::setDescription);
        Optional.ofNullable(request.getAddress()).ifPresent(organisation::setAddress);
        organisationService.saveOrganisation(organisation);

        log.info("updateOrganisation -> done");
    }

    @Override
    public void deleteOrganisation(Long id) {
        log.info("deleteOrganisation -> start");

        validationHelper.validateForCorrectId(id, "OrganisationId");

        if (organisationService.existsById(id)) {
            organisationService.deleteOrganisation(id);
        } else {
            log.warn("NoOrganisationFoundException is thrown");
            throw new NoOrganisationFoundException(
              "No Organisation with id = " + id + " found"
            );
        }

        log.info("deleteOrganisation -> done");
    }

    @Override
    @Transactional
    public void suspendOrganisation(Long orgId) {
        log.info("suspendOrganisation -> start");

        validationHelper.validateForCorrectId(orgId, "OrganisationId");

        Optional<Organisation> optionalOrganisation = organisationService.getOrganisation(orgId);
        Organisation organisation;
        if (optionalOrganisation.isEmpty()) {
            log.warn("NoOrganisationFoundException is thrown");
            throw new NoOrganisationFoundException(
                    "No organisation with id = " + orgId + " found"
            );
        } else {
            organisation = optionalOrganisation.get();
        }

        if (organisation.isActive()) {
            organisation.setActive(false);
            organisationService.saveOrganisation(organisation);
        } else {
            log.warn("OrganisationIsAlreadySuspendedException is thrown");
            throw new OrganisationIsAlreadySuspendedException(
                    "Organisation with id = " + orgId + " is already suspended"
            );
        }

        var workers = workerService.findAllWorkersOfOrganisation(orgId);

        workers.forEach(worker -> {
            var workersRecords = scheduleRecordService.findAllRecordsOfWorker(worker.getId());

            workersRecords.forEach(record -> scheduleRecordFacade.cancelRecord(record.getId()));
        });
        //TODO check if correct

        log.info("suspendOrganisation -> done");
    }

    @Override
    @Transactional
    public void launchOrganisation(Long id) {
        log.info("launchOrganisation -> start");

        validationHelper.validateForCorrectId(id, "OrganisationId");

        Optional<Organisation> optionalOrganisation = organisationService.getOrganisation(id);
        Organisation organisation;
        if (optionalOrganisation.isEmpty()) {
            log.warn("NoOrganisationFoundException is thrown");
            throw new NoOrganisationFoundException(
                    "No organisation with id = " + id + " found"
            );
        } else {
            organisation = optionalOrganisation.get();
        }

        if (!organisation.isActive()) {
            organisation.setActive(true);
            organisationService.saveOrganisation(organisation);
        } else {
            log.warn("OrganisationIsAlreadyLaunched is thrown");
            throw new OrganisationIsAlreadyLaunchedException(
                    "Organisation with id = " + id + " is already running"
            );
        }

        log.info("launchOrganisation -> done");
    }

    private void validatePageAndSize(int page, int size) {
        log.info("validatePageAndSize -> start");

        if (page < 0) {
            log.warn("IllegalArgumentException is thrown");
            throw new IllegalArgumentException(
                    "Page should be positive or zero"
            );
        }

        if (size <= 0) {
            log.warn("IllegalArgumentException is thrown");
            throw new IllegalArgumentException(
                    "Size must be positive"
            );
        }
        log.info("validatePageAndOrganisation -> done");
    }
}
