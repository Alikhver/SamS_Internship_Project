package com.alikhver.web.facade;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.Worker;
import com.alikhver.model.service.OrganisationService;
import com.alikhver.model.service.WorkerService;
import com.alikhver.web.converter.worker.WorkerConverter;
import com.alikhver.web.dto.worker.request.CreateWorkerRequest;
import com.alikhver.web.dto.worker.request.UpdateWorkerRequest;
import com.alikhver.web.dto.worker.response.CreateWorkerResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import com.alikhver.web.exeption.organisation.NoOrganisationFoundException;
import com.alikhver.web.exeption.worker.NoWorkerFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkerFacadeImpl implements WorkerFacade {
    private final OrganisationService organisationService;
    private final WorkerService workerService;
    private final WorkerConverter workerConverter;

    @Override
    public GetWorkerResponse getWorkerById(Long id) {
        Optional<Worker> optionalWorker = workerService.getWorker(id);
        if (optionalWorker.isPresent()) {
            Worker worker = optionalWorker.get();
            return workerConverter.mapToGetWorkerResponse(worker);
        } else {
            throw new NoWorkerFoundException(
                    "No Worker with id = " + id + " found"
            );
        }
    }

    @Override
    @Transactional
    public void updateWorker(Long id, UpdateWorkerRequest request) {
        Optional<Worker> optionalWorker = workerService.getWorker(id);
        Worker worker;
        if (optionalWorker.isPresent()) {
            worker = optionalWorker.get();
        } else {
            throw new NoWorkerFoundException(
                    "No Worker with id = " + id + " found"
            );
        }
        Optional.ofNullable(request.getFirstName()).ifPresent(worker::setFirstName);
        Optional.ofNullable(request.getLastName()).ifPresent(worker::setLastName);
        Optional.ofNullable(request.getDescription()).ifPresent(worker::setDescription);

        workerService.saveWorker(worker);
    }

    @Override
    @Transactional
    public void deleteWorker(Long id) {
        if (workerService.existsWorkerById(id)) {
            workerService.deleteWorker(id);
        } else {
            throw new NoWorkerFoundException(
              "No Worker with id = " + id + " found"
            );
        }
    }

    @Override
    @Transactional
    public CreateWorkerResponse createWorker(CreateWorkerRequest request) {
        Optional<Organisation> optionalOrganisation = organisationService
                .getOrganisation(request.getOrganisationId());
        Organisation organisation;
        if (optionalOrganisation.isPresent()) {
            organisation = optionalOrganisation.get();
        } else {
            throw new NoOrganisationFoundException(
              "No Organisation with id = " + request.getOrganisationId() + " found"
            );
        }
        Worker worker = workerConverter.mapToWorker(request);
        worker.setOrganisation(organisation);
        workerService.saveWorker(worker);
        return workerConverter.mapToCreateWorkerResponse(worker);
    }
}
