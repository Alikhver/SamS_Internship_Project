package com.alikhver.web.converter.scheduleRecord;

import com.alikhver.model.entity.ScheduleRecord;
import com.alikhver.model.entity.ScheduleRecordStatus;
import com.alikhver.web.converter.profile.ProfileToGetProfileResponseConverter;
import com.alikhver.web.converter.utility.UtilityToGetUtilityResponseConverter;
import com.alikhver.web.converter.worker.WorkerToGetWorkerResponseConverter;
import com.alikhver.web.dto.record.response.GetRecordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleRecordToGetRecordResponseConverter implements Converter<ScheduleRecord, GetRecordResponse> {
    private final ProfileToGetProfileResponseConverter profileConverter;
    private final UtilityToGetUtilityResponseConverter utilityConverter;
    private final WorkerToGetWorkerResponseConverter workerConverter;

    @Override
    public GetRecordResponse convert(ScheduleRecord source) {
        GetRecordResponse response = GetRecordResponse.builder()
                .id(source.getId())
                .status(source.getStatus().toString())
                .date(source.getDate())
                .worker(workerConverter.convert(source.getWorker()))
                .build();

        if (source.getStatus() != ScheduleRecordStatus.AVAILABLE) {
            response.setProfile(profileConverter.convert(source.getClientProfile()));
            response.setUtility(utilityConverter.convert(source.getUtility()));
        }

        return response;
    }
}
