package com.alikhver.web.converter.scheduleRecord;

import com.alikhver.model.entity.ScheduleRecord;
import com.alikhver.model.entity.ScheduleRecordStatus;
import com.alikhver.web.dto.record.request.CreateRecordRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateRecordRequestToScheduleRecordConverter implements Converter<CreateRecordRequest, ScheduleRecord> {

    @Override
    public ScheduleRecord convert(CreateRecordRequest source) {
        return ScheduleRecord.builder()
                .date(source.getDate())
                .status(ScheduleRecordStatus.AVAILABLE)
                .build();
    }
}
