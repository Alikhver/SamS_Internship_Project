package com.alikhver.web.dto.organisation.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
@Builder
public class UpdateOrganisationRequest {
    @Size(min = 2, max = 30)
    private String name;
    @Size(min = 30, max = 300)
    private String description;
    @Size(min = 5, max = 45)
    private String address;
}
