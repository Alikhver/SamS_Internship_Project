package com.alikhver.web.dto.organisation.request;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class UpdateOrganisationRequest {
    @Size(min = 2, max = 30)
    private String name;
    @Size(min = 30, max = 120)
    private String description;
    @Size(min = 5, max = 45)
    private String address;
}
