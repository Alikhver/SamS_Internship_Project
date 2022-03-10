package com.alikhver.web.facade;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.Worker;
import com.alikhver.model.service.OrganisationService;
import com.alikhver.model.service.WorkerService;
import com.alikhver.model.util.ValidationHelper;
import com.alikhver.web.converter.worker.WorkerConverter;
import com.alikhver.web.dto.worker.request.CreateWorkerRequest;
import com.alikhver.web.dto.worker.request.UpdateWorkerRequest;
import com.alikhver.web.dto.worker.response.CreateWorkerResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import com.alikhver.web.exeption.organisation.NoOrganisationFoundException;
import com.alikhver.web.exeption.worker.NoWorkerFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkerFacadeImpl implements WorkerFacade {
    private final OrganisationService organisationService;
    private final WorkerService workerService;
    private final WorkerConverter workerConverter;
    private final ValidationHelper validationHelper;

    @Override
    public GetWorkerResponse getWorkerById(Long id) {
        log.info("workerFacade::getWorkerById -> start");

        Optional<Worker> optionalWorker = workerService.getWorker(id);
        if (optionalWorker.isPresent()) {
            Worker worker = optionalWorker.get();

            var response = workerConverter.mapToGetWorkerResponse(worker);

            log.info("workerFacade::getWorkerById -> done");
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
        log.info("workerFacade::updateWorker -> start");

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
        log.info("workerFacade::updateWorker -> done");
    }

    @Override
    @Transactional
    public void deleteWorker(Long id) {
        log.info("workerFacade::deleteWorker -> start");

        validationHelper.validateForCorrectId(id, "WorkerId");

        if (workerService.existsWorker(id)) {
            workerService.deleteWorker(id);
            log.info("workerFacade::deleteWorker -> done");
        } else {
            log.warn("NoWorkerFoundException");
            throw new NoWorkerFoundException(
              "No Worker with id = " + id + " found"
            );
        }
    }

    @Override
    @Transactional
    public CreateWorkerResponse createWorker(CreateWorkerRequest request) {
        log.info("workerFacade::createWorker -> start");

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

        log.info("workerFacade::createWorker -> done");
        return response;
    }
}
