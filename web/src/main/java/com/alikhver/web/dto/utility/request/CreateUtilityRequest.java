package com.alikhver.web.dto.utility.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
public class CreateUtilityRequest {
    @NotBlank
    private String name;
    @Positive
    private Double price;
    @NotBlank
    private String description;
    @Positive
    private Long organisationId;
}
