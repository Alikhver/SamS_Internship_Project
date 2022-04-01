package com.alikhver.model.service;

import com.alikhver.model.entity.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WorkerService {
    Optional<Worker> getWorker(Long workerId);

    void saveWorker(Worker worker);

    boolean existsWorker(Long workerId);

    void deleteWorker(Long workerId);

    Page<Worker> findAllWorkersOfOrganisation(Long organisationId, Pageable pageable);

    boolean workerAlreadyHasUtility(Long workerId, Long utilityId);

    Page<Worker> getWorkersByUtilityId(Long utilityId, Pageable pageable);
}
