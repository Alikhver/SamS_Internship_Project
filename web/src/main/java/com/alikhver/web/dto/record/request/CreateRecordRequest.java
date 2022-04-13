package com.alikhver.web.dto.record.request;

import lombok.Getter;

import java.util.Date;

@Getter
public class CreateRecordRequest {
    private Date date;
    private Long profileId;
    private Long workerId;
    private Long utilityId;
}
