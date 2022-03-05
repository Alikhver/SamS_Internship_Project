package com.alikhver.model.service;

import com.alikhver.model.entity.Worker;

import java.util.Optional;

public interface WorkerService {
    Optional<Worker> getWorkerById(Long id);
    void saveWorker(Worker worker);
}
