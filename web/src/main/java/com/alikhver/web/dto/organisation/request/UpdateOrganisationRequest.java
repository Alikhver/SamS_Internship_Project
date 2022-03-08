package com.alikhver.web.dto.organisation.request;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class UpdateOrganisationRequest {
    @Size(min = 2, max = 27)
    private String name;
    @Size(min = 8)
    private String description;
    private String address;
}
