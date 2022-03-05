package com.alikhver.model.service;

import com.alikhver.model.entity.Worker;
import com.alikhver.model.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {
    private final WorkerRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Worker> getWorker(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsWorkerById(Long id) {
        return repository.existsWorkerById(id);
    }

    @Override
    public void deleteWorker(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void saveWorker(Worker worker) {
        repository.save(worker);
    }
}
