package com.alikhver.web.converter.scheduleRecord;

import com.alikhver.model.entity.ScheduleRecord;
import com.alikhver.web.dto.record.response.GetRecordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ScheduleRecordToGetRecordResponseConverter implements Converter<ScheduleRecord, GetRecordResponse> {

    @Override
    public GetRecordResponse convert(ScheduleRecord source) {
        GetRecordResponse response = GetRecordResponse.builder()
                .id(source.getId())
                .status(source.getStatus().toString())
                .date(source.getDate())
                .workerId(source.getWorker().getId())
                .build();

        Optional.ofNullable(response.getProfileId()).ifPresent(response::setProfileId);
        Optional.ofNullable(response.getUtilityId()).ifPresent(response::setUtilityId);

        return response;
    }
}
