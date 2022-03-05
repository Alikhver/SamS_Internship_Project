package com.alikhver.model.service;

import com.alikhver.model.entity.Worker;

import java.util.Optional;

public interface WorkerService {
    Optional<Worker> getWorker(Long id);
    void saveWorker(Worker worker);
    boolean existsWorkerById(Long id);
    void deleteWorker(Long id);
}
