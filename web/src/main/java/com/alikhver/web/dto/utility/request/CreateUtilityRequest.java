package com.alikhver.web.dto.utility.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
public class CreateUtilityRequest {
    @NotBlank
    @Size(max = 40)
    private String name;
    @Positive
    private Double price;
    @NotBlank
    @Size(min = 15)
    private String description;
    @Positive
    private Long organisationId;
}
