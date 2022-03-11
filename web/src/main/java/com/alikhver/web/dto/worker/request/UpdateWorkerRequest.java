package com.alikhver.web.dto.worker.request;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class UpdateWorkerRequest {
    @Size(min = 3, max = 45)
    private String firstName;
    @Size(min = 2, max = 45)
    private String lastName;
    @Size(min = 15)
    private String description;
}
