package com.alikhver.web.dto.utility.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUtilityResponse {
    private long id;
    private String name;
    private String description;
    private double price;
}
