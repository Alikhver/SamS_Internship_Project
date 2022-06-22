package com.alikhver.web.converter.scheduleRecord;

import com.alikhver.model.entity.Profile;
import com.alikhver.model.entity.ScheduleRecord;
import com.alikhver.model.entity.Utility;
import com.alikhver.web.dto.record.response.GetRecordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ScheduleRecordToGetRecordResponseConverter implements Converter<ScheduleRecord, GetRecordResponse> {

    @Override
    @Transactional
    public GetRecordResponse convert(ScheduleRecord source) {
        GetRecordResponse response = GetRecordResponse.builder()
                .id(source.getId())
                .status(source.getStatus().toString())
                .date(source.getDate())
                .workerId(source.getWorker().getId())
                .build();

        Optional.ofNullable(source.getClientProfile()).map(Profile::getId).ifPresent(response::setProfileId);
        Optional.ofNullable(source.getUtility()).map(Utility::getId).ifPresent(response::setUtilityId);

        return response;
    }
}
