package com.alikhver.web.dto.worker.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateWorkerResponse {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final String description;
    private final long organisationId;
}
