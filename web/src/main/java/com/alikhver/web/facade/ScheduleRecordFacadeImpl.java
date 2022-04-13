package com.alikhver.web.facade;

import com.alikhver.model.entity.ScheduleRecord;
import com.alikhver.model.service.ScheduleRecordService;
import com.alikhver.web.converter.scheduleRecord.ScheduleRecordConverter;
import com.alikhver.web.dto.record.response.GetRecordResponse;
import com.alikhver.web.exception.schedule_record.NoScheduleRecordFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleRecordFacadeImpl implements ScheduleRecordFacade {
    private final ScheduleRecordService scheduleRecordService;
    private final ScheduleRecordConverter scheduleRecordConverter;

    @Override
    public GetRecordResponse getRecord(Long recordId) {
        log.info("getRecord -> start");

        ScheduleRecord record = scheduleRecordService.get(recordId).orElseThrow(() ->
                new NoScheduleRecordFoundException(
                        "ScheduleRecord with id = " + recordId + " found"
                )
        );

        var response = scheduleRecordConverter.mapToGetRecordResponse(record);

        log.info("getRecord -> done");
        return response;
    }
}
