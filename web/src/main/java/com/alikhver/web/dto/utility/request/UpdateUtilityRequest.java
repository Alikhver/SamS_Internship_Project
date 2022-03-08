package com.alikhver.web.dto.utility.request;

import lombok.Getter;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
public class UpdateUtilityRequest {
    @Size(max = 40)
    private String name;
    @Positive
    private Double price;
    @Size(min = 15)
    private String description;
}
