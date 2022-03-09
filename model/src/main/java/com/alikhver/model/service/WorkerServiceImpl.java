package com.alikhver.model.service;

import com.alikhver.model.entity.Worker;
import com.alikhver.model.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {
    private final WorkerRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Worker> get(Long workerId) {
        assert (workerId > 0);
        return repository.findById(workerId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(Long workerId) {
        assert (workerId > 0);
        return repository.existsWorkerById(workerId);
    }

    @Override
    public void delete(Long workerId) {
        assert (workerId > 0);
        repository.deleteById(workerId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Worker> findAllWorkersOfOrganisation(Long organisationId, Pageable pageable) {
        assert (organisationId > 0);
        return repository.findAllByOrganisationId(organisationId, pageable);
    }

    @Override
    public void save(Worker worker) {
        Objects.requireNonNull(worker.getFirstName());
        Objects.requireNonNull(worker.getLastName());
        Objects.requireNonNull(worker.getDescription());
        Objects.requireNonNull(worker.getOrganisation());

        repository.save(worker);
    }
}
