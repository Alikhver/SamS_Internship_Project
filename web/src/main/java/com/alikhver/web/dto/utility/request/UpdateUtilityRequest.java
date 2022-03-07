package com.alikhver.web.dto.utility.request;

import lombok.Getter;

@Getter
public class UpdateUtilityRequest {
    private String name;
    private Double price;
    private String description;
}
