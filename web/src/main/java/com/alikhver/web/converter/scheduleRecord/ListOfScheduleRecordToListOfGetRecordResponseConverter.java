package com.alikhver.web.converter.scheduleRecord;

import com.alikhver.model.entity.ScheduleRecord;
import com.alikhver.web.dto.record.response.GetRecordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ListOfScheduleRecordToListOfGetRecordResponseConverter implements Converter<List<ScheduleRecord>, List<GetRecordResponse>> {
    private final ScheduleRecordToGetRecordResponseConverter scheduleRecordToGetRecordResponseConverter;

    @Override
    public List<GetRecordResponse> convert(List<ScheduleRecord> source) {
        return source.stream().map(scheduleRecordToGetRecordResponseConverter::convert).collect(Collectors.toList());
    }
}
