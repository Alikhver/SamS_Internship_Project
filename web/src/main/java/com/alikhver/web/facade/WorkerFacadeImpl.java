package com.alikhver.web.facade;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.Utility;
import com.alikhver.model.entity.Worker;
import com.alikhver.model.service.OrganisationService;
import com.alikhver.model.service.ScheduleRecordService;
import com.alikhver.model.service.UtilityService;
import com.alikhver.model.service.WorkerService;
import com.alikhver.model.util.ValidationHelper;
import com.alikhver.web.converter.scheduleRecord.ScheduleRecordConverter;
import com.alikhver.web.converter.utility.UtilityConverter;
import com.alikhver.web.converter.worker.WorkerConverter;
import com.alikhver.web.dto.record.response.GetRecordResponse;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import com.alikhver.web.dto.worker.request.CreateWorkerRequest;
import com.alikhver.web.dto.worker.request.UpdateWorkerRequest;
import com.alikhver.web.dto.worker.response.CreateWorkerResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import com.alikhver.web.exception.organisation.NoOrganisationFoundException;
import com.alikhver.web.exception.utility.NoUtilityFoundException;
import com.alikhver.web.exception.utility.UtilityAlreadyHasProvidedWorkerException;
import com.alikhver.web.exception.utility.UtilityDoesNotHaveProvidedWorkerException;
import com.alikhver.web.exception.worker.AttemptToAssignUtilityOfOtherOrganisationException;
import com.alikhver.web.exception.worker.AttemptToDeleteUtilityFromWorkerOfOtherOrganisationException;
import com.alikhver.web.exception.worker.NoWorkerFoundException;
import com.alikhver.web.exception.worker.WorkerAlreadyHasProvidedUtilityException;
import com.alikhver.web.exception.worker.WorkerDoesNotHaveProvidedUtilityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkerFacadeImpl implements WorkerFacade {
    private final OrganisationService organisationService;
    private final WorkerService workerService;
    private final UtilityService utilityService;
    private final ScheduleRecordService scheduleRecordService;
    private final WorkerConverter workerConverter;
    private final ValidationHelper validationHelper;
    private final UtilityConverter utilityConverter;
    private final ScheduleRecordConverter scheduleRecordConverter;

    @Override
    public GetWorkerResponse getWorkerById(Long id) {
        log.info("getWorkerById -> start");

        Optional<Worker> optionalWorker = workerService.getWorker(id);
        if (optionalWorker.isPresent()) {
            Worker worker = optionalWorker.get();

            var response = workerConverter.mapToGetWorkerResponse(worker);

            log.info("getWorkerById -> done");
            return response;
        } else {
            log.warn("NoWorkerFoundException is thrown");
            throw new NoWorkerFoundException(
                    "No Worker with id = " + id + " found"
            );
        }
    }

    @Override
    @Transactional
    public void updateWorker(Long id, UpdateWorkerRequest request) {
        log.info("updateWorker -> start");

        validationHelper.validateForCorrectId(id, "WorkerId");

        Optional<Worker> optionalWorker = workerService.getWorker(id);
        Worker worker;
        if (optionalWorker.isPresent()) {
            worker = optionalWorker.get();
        } else {
            log.warn("NoWorkerFoundException is thrown");
            throw new NoWorkerFoundException(
                    "No Worker with id = " + id + " found"
            );
        }
        Optional.ofNullable(request.getFirstName()).ifPresent(worker::setFirstName);
        Optional.ofNullable(request.getLastName()).ifPresent(worker::setLastName);
        Optional.ofNullable(request.getDescription()).ifPresent(worker::setDescription);

        workerService.saveWorker(worker);
        log.info("updateWorker -> done");
    }

    @Override
    @Transactional
    public void deleteWorker(Long id) {
        log.info("deleteWorker -> start");

        validationHelper.validateForCorrectId(id, "WorkerId");

        if (workerService.existsWorker(id)) {
            workerService.deleteWorker(id);
            log.info("deleteWorker -> done");
        } else {
            log.warn("NoWorkerFoundException");
            throw new NoWorkerFoundException(
                    "No Worker with id = " + id + " found"
            );
        }
    }

    @Override
    @Transactional
    public void addUtility(Long id, Long utilityId) {
        log.info("addUtility -> start");

        validationHelper.validateForCorrectId(id, "WorkerId");
        validationHelper.validateForCorrectId(utilityId, "UtilityId");

        Optional<Worker> optionalWorker = workerService.getWorker(id);
        Worker worker;
        if (optionalWorker.isPresent()) {
            worker = optionalWorker.get();
        } else {
            log.warn("NoWorkerFoundException is thrown");
            throw new NoWorkerFoundException(
                    "Worker with id = " + id + " was not found"
            );
        }

        Optional<Utility> optionalUtility = utilityService.getUtility(utilityId);
        Utility utility;

        if (optionalUtility.isPresent()) {
            utility = optionalUtility.get();
        } else {
            log.warn("NoUtilityFoundException is thrown");
            throw new NoUtilityFoundException(
                    "Utility with id = " + id + " was not found"
            );
        }

        if (!Objects.equals(utility.getOrganisation().getId(), worker.getOrganisation().getId())) {
            log.warn("AttemptToAssignUtilityOfOtherOrganisationException is thrown");
            throw new AttemptToAssignUtilityOfOtherOrganisationException(
                    "Utility belongs to other Organisation than Worker"
            );
        } else if (utilityService.utilityAlreadyHasWorker(utilityId, id)) {
            log.warn("UtilityAlreadyHasProvidedWorkerException is thrown");
            throw new UtilityAlreadyHasProvidedWorkerException(
                    "Utility with id = " + utilityId + " already has worker with id = " + id
            );
        } else if (workerService.workerAlreadyHasUtility(id, utilityId)) {
            log.warn("WorkerAlreadyHasProvidedUtilityException is thrown");
            throw new WorkerAlreadyHasProvidedUtilityException(
                    "Worker with id = " + id + " already has utility with id = " + utilityId
            );
        } else {
            worker.getUtilities().add(utility);
            utility.getWorkers().add(worker);
        }

        workerService.saveWorker(worker);
        utilityService.saveUtility(utility);

        log.info("addUtility -> done");
    }

    @Override
    @Transactional
    public void deleteUtility(Long workerId, Long utilityId) {
        log.info("deleteUtility -> start");

        validationHelper.validateForCorrectId(workerId, "WorkerId");
        validationHelper.validateForCorrectId(utilityId, "UtilityId");

        Optional<Worker> optionalWorker = workerService.getWorker(workerId);
        Worker worker;
        if (optionalWorker.isPresent()) {
            worker = optionalWorker.get();
        } else {
            log.warn("NoWorkerFoundException is thrown");
            throw new NoWorkerFoundException(
                    "Worker with id = " + workerId + " was not found"
            );
        }

        Optional<Utility> optionalUtility = utilityService.getUtility(utilityId);
        Utility utility;

        if (optionalUtility.isPresent()) {
            utility = optionalUtility.get();
        } else {
            log.warn("NoUtilityFoundException is thrown");
            throw new NoUtilityFoundException(
                    "Utility with id = " + workerId + " was not found"
            );
        }

        if (!Objects.equals(utility.getOrganisation().getId(), worker.getOrganisation().getId())) {
            log.warn("AttemptToDeleteUtilityOfOtherOrganisationException is thrown");
            throw new AttemptToDeleteUtilityFromWorkerOfOtherOrganisationException(
                    "Utility belongs to other Organisation than Worker"
            );
        } else if (!utilityService.utilityAlreadyHasWorker(utilityId, workerId)) {
            log.warn("UtilityDoesNotHaveProvidedWorkerException is thrown");
            throw new UtilityDoesNotHaveProvidedWorkerException(
                    "Utility with id = " + utilityId + " does not have worker with id = " + workerId
            );
        } else if (!workerService.workerAlreadyHasUtility(workerId, utilityId)) {
            log.warn("WorkerDoesNotHaveProvidedUtilityException is thrown");
            throw new WorkerDoesNotHaveProvidedUtilityException(
                    "Worker with id = " + workerId + " does not have utility with id = " + utilityId
            );

        }

        worker.getUtilities().removeIf(ut -> ut.getId().equals(utilityId));
        utility.getWorkers().removeIf(w -> w.getId().equals(workerId));

        workerService.saveWorker(worker);
        utilityService.saveUtility(utility);

        log.info("deleteUtility -> done");
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GetUtilityResponse> getUtilitiesOfWorker(Long workerId, int page, int size) {
        log.info("getUtilitiesOfWorker -> start");

        validationHelper.validateForCorrectId(workerId, "WorkerId");
        if (!workerService.existsWorker(workerId)) {
            throw new NoWorkerFoundException(
                    "Worker with id " + workerId + " was not found"
            );
        }
        Pageable pageable = PageRequest.of(page, size);
        var utilities = utilityService.getUtilitiesByWorkerId(workerId, pageable);

        var response = utilityConverter.mapToPageOfGetUtilityResponse(utilities);

        log.info("getUtilitiesOfWorker -> done");
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetRecordResponse> getAllFutureRecords(Long workerId) {
        log.info("getAllFutureRecord -> start");

        if (!workerService.existsWorker(workerId)) {
            log.warn("NoWorkerFoundException is thrown");
            throw new NoWorkerFoundException(
              "No Worker with id = " + workerId + " found"
            );
        }

        var futureRecords = scheduleRecordService.findAllRecordsOfWorker(workerId);
        var response = scheduleRecordConverter.mapToListOfGetRecordResponse(futureRecords);

        log.info("getAllFutureRecord -> done");
        return response;
    }

    @Override
    public List<GetUtilityResponse> getUtilitiesOfWorker(Long workerId) {
        log.info("getUtilitiesOfWorker -> start");

        validationHelper.validateForCorrectId(workerId, "WorkerId");
        if (!workerService.existsWorker(workerId)) {
            throw new NoWorkerFoundException(
                    "Worker with id " + workerId + " was not found"
            );
        }
        List<Utility> utilities = utilityService.getUtilitiesByWorkerId(workerId);

        var response = utilityConverter.mapToListOfGetUtilityResponse(utilities);

        log.info("getUtilitiesOfWorker -> done");
        return response;
    }

    @Override
    @Transactional
    public CreateWorkerResponse createWorker(CreateWorkerRequest request) {
        log.info("createWorker -> start");

        Optional<Organisation> optionalOrganisation = organisationService
                .getOrganisation(request.getOrganisationId());
        Organisation organisation;
        if (optionalOrganisation.isPresent()) {
            organisation = optionalOrganisation.get();
        } else {
            log.warn("NoOrganisationFoundException is thrown");
            throw new NoOrganisationFoundException(
                    "No Organisation with id = " + request.getOrganisationId() + " found"
            );
        }
        Worker worker = workerConverter.mapToWorker(request);
        worker.setOrganisation(organisation);
        workerService.saveWorker(worker);

        var response = workerConverter.mapToCreateWorkerResponse(worker);

        log.info("createWorker -> done");
        return response;
    }
}

