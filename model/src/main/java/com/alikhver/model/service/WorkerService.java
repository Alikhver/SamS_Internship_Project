package com.alikhver.model.service;

import com.alikhver.model.entity.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WorkerService {
    Optional<Worker> getWorker(Long id);

    void saveWorker(Worker worker);

    boolean existsWorkerById(Long id);

    void deleteWorker(Long id);

    Page<Worker> findAllWorkersOfOrganisation(Long organisationId, Pageable pageable);
}
