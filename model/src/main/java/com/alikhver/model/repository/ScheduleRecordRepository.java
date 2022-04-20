package com.alikhver.model.repository;

import com.alikhver.model.entity.ScheduleRecord;
import com.alikhver.model.entity.ScheduleRecordStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ScheduleRecordRepository extends JpaRepository<ScheduleRecord, Long> {
    boolean existsByWorkerIdAndDateAndStatus(Long workerId, Date date, ScheduleRecordStatus status);

    List<ScheduleRecord> findAllByDateAfter(Date date);

    List<ScheduleRecord> findAllByWorkerId(Long workerId);

    Optional<ScheduleRecord> findByWorkerIdAndDateAndStatus(Long workerId, Date date, ScheduleRecordStatus status);

    List<ScheduleRecord> findAllByDate(Date date);

    List<ScheduleRecord> findAllByWorkerIdAndDateAfter(Long workerId, Date date);
}
