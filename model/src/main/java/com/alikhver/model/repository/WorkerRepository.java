package com.alikhver.model.repository;

import com.alikhver.model.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    boolean existsWorkerById(Long id);
    List<Worker> findAllByOrganisationId(Long id);
}
