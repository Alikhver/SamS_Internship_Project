package com.alikhver.web.dto.worker.request;

import lombok.Getter;

@Getter
public class UpdateWorkerRequest {
    private String firstName;
    private String lastName;
    private String description;
}
