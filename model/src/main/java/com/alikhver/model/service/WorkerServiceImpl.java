package com.alikhver.model.service;

import com.alikhver.model.entity.Organisation;
import com.alikhver.model.entity.Worker;
import com.alikhver.model.repository.WorkerRepository;
import com.alikhver.model.util.ValidationHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {
    private final WorkerRepository repository;
    private final ValidationHelper validationHelper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Worker> getWorker(Long workerId) {
        log.info("getWorker -> start");

        validationHelper.validateForCorrectId(workerId, "WorkerId");
        var worker = repository.findById(workerId);

        log.info("getWorker -> done");
        return worker;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsWorker(Long workerId) {
        log.info("existsWorker -> start");

        validationHelper.validateForCorrectId(workerId, "WorkerId");
        var exists = repository.existsWorkerById(workerId);

        log.info("existsWorker -> done");
        return exists;
    }

    @Override
    public void deleteWorker(Long workerId) {
        log.info("deleteWorker -> start");

        validationHelper.validateForCorrectId(workerId, "WorkerId");

        log.info("deleteWorker -> done");
        repository.deleteById(workerId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Worker> findAllWorkersOfOrganisation(Long organisationId, Pageable pageable) {
        log.info("findAllWorkersOfOrganisation -> start");

        validationHelper.validateForCorrectId(organisationId, "OrganisationId");
        var workers = repository.findAllByOrganisationId(organisationId, pageable);

        log.info("findAllWorkersOfOrganisation -> done");
        return workers;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean workerAlreadyHasUtility(Long workerId, Long utilityId) {
        log.info("workerAlreadyHasUtility -> start");

        boolean response = repository.existsWorkerByIdAndUtilitiesId(workerId, utilityId);

        log.info("workerAlreadyHasUtility -> done");
        return response;
    }

    @Override
    public Page<Worker> getWorkersByUtilityId(Long utilityId, Pageable pageable) {
        log.info("getWorkersByUtilityId -> start");

        validationHelper.validateForCorrectId(utilityId, "UtilityId");
        var workers = repository.findWorkerByUtilitiesId(utilityId, pageable);

        log.info("getWorkersByUtilityId -> done");
        return workers;
    }

    @Override
    public void saveWorker(Worker worker) {
        log.info("saveWorker -> start");

        validateWorker(worker);

        log.info("saveWorker -> done");
        repository.save(worker);
    }

    private void validateWorker(Worker worker) {
        log.info("validateWorker -> start");

        validationHelper.validateForCorrectString(worker.getFirstName(), "Worker First Name");
        validationHelper.validateForCorrectString(worker.getDescription(), "Worker Last Name");
        validationHelper.validateForCorrectString(worker.getDescription(), "Worker Description");

        log.info("validateWorker -> done");
        validateOrganisation(worker.getOrganisation());
    }

    private void validateOrganisation(Organisation organisation) {
        log.info("validateOrganisation -> start");

        validationHelper.validateForCorrectString(organisation.getName(), "Organisation Name");
        validationHelper.validateForCorrectString(organisation.getDescription(), "Organisation Description");
        if (organisation.getDateCreated() == null) organisation.setDateCreated(new Date());

        log.info("validateOrganisation -> done");
    }
}
