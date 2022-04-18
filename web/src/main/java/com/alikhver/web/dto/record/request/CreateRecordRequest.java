package com.alikhver.web.dto.record.request;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class CreateRecordRequest {
    private Date date;
    private Long workerId;
}
