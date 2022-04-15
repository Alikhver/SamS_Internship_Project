package com.alikhver.model.repository;

import com.alikhver.model.entity.ScheduleRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface ScheduleRecordRepository extends JpaRepository<ScheduleRecord, Long> {
    boolean existsByWorkerIdAndDate(Long workerId, Date date);
}
