package com.alikhver.model.repository;

import com.alikhver.model.entity.ScheduleRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRecordRepository extends JpaRepository<ScheduleRecord, Long> {
}
