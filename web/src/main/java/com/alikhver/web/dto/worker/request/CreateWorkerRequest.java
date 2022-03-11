package com.alikhver.web.dto.worker.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
public class CreateWorkerRequest {
    @NotBlank
    @Size(min = 3, max = 45)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 45)
    private String lastName;
    @NotBlank
    @Size(min = 15)
    private String description;
    @Positive
    @NotNull
    private long organisationId;
}
