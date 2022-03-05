package com.alikhver.web.dto.worker.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UpdateWorkerRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String description;
}
