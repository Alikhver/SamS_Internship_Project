package com.alikhver.model.service;

import com.alikhver.model.entity.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WorkerService {
    Optional<Worker> get(Long workerId);

    void save(Worker worker);

    boolean exists(Long workerId);

    void delete(Long workerId);

    Page<Worker> findAllWorkersOfOrganisation(Long organisationId, Pageable pageable);
}
