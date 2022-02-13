package com.alikhver.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRecordRepository extends JpaRepository<ScheduleRecordRepository, Long> {
}
