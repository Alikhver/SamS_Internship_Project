package com.alikhver.web.converter.scheduleRecord;


import com.alikhver.model.entity.ScheduleRecord;
import com.alikhver.web.dto.record.request.CreateRecordRequest;
import com.alikhver.web.dto.record.response.GetRecordResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduleRecordConverter {
    private final ScheduleRecordToGetRecordResponseConverter scheduleRecordToGetRecordResponseConverter;
    private final CreateRecordRequestToScheduleRecordConverter createRecordRequestToScheduleRecordConverter;

    public GetRecordResponse mapToGetRecordResponse(ScheduleRecord record) {
        log.info("mapToGetRecordResponse -> start");

        var response = scheduleRecordToGetRecordResponseConverter.convert(record);

        log.info("mapToGetRecordResponse -> done");
        return response;
    }

    public ScheduleRecord mapToScheduleRecord(CreateRecordRequest request) {
        log.info("mapToScheduleRecord -> start");

        ScheduleRecord response = createRecordRequestToScheduleRecordConverter.convert(request);

        log.info("mapToScheduleRecord -> done");
        return response;
    }
}
