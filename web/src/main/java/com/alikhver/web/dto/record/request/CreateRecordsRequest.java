package com.alikhver.web.dto.record.request;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Future;
import javax.validation.constraints.Positive;
import java.util.Date;

@Getter
public class CreateRecordsRequest {
    @Future
    private Date date;
    @Range(max = 23)
    private int startTime;
    @Range(min = 1L, max = 24)
    private int endTime;
    @Positive
    private Long workerId;
}
