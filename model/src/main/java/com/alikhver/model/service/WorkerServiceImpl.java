package com.alikhver.model.service;

import com.alikhver.model.entity.Worker;
import com.alikhver.model.repository.WorkerRepository;
import com.alikhver.model.service.util.ServiceValidationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {
    private final WorkerRepository repository;
    private final ServiceValidationHelper validationHelper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Worker> get(Long workerId) {
        validationHelper.validateForCorrectId(workerId, "WorkerId");
        return repository.findById(workerId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(Long workerId) {
        validationHelper.validateForCorrectId(workerId, "WorkerId");
        return repository.existsWorkerById(workerId);
    }

    @Override
    public void delete(Long workerId) {
        validationHelper.validateForCorrectId(workerId, "WorkerId");
        repository.deleteById(workerId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Worker> findAllWorkersOfOrganisation(Long organisationId, Pageable pageable) {
        validationHelper.validateForCorrectId(organisationId, "OrganisationId");
        return repository.findAllByOrganisationId(organisationId, pageable);
    }

    @Override
    public void save(Worker worker) {
        validationHelper.validateWorker(worker);
        repository.save(worker);
    }
}
