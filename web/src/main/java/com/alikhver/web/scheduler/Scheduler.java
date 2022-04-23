package com.alikhver.web.scheduler;

import com.alikhver.model.entity.ScheduleRecord;
import com.alikhver.model.entity.ScheduleRecordStatus;
import com.alikhver.model.service.ScheduleRecordService;
import com.alikhver.web.facade.ScheduleRecordFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class Scheduler {
    private final ScheduleRecordService recordService;
    private final ScheduleRecordFacade recordFacade;

    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron = "@hourly")
    public void finishScheduleRecords() {
        log.info("finishScheduleRecords -> start");

        long hour = 60 * 60 * 1000;

//        LocalDateTime current = LocalDateTime.now();
        Date current = new Date();

        current.setMinutes(0);
        current.setSeconds(0);

        List<ScheduleRecord> records;

        //records with status AVAILABLE -> EXPIRED
        records = recordService.getUtilitiesByTime(current);

        records.forEach(record -> {
            Long id = record.getId();
            ScheduleRecordStatus status = record.getStatus();

            if (status == ScheduleRecordStatus.AVAILABLE) {
                recordFacade.setRecordExpired(id);
            }
        });

        //records with status BOOKED -> DONE
        current.setTime(current.getTime() - hour);
        records = recordService.getUtilitiesByTime(current);

        records.forEach(record -> {
            Long id = record.getId();
            ScheduleRecordStatus status = record.getStatus();

            if (status == ScheduleRecordStatus.BOOKED) {
                recordFacade.setRecordDone(id);
            }
        });

        log.info("finishScheduleRecords -> done");
    }
}
