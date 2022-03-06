package com.alikhver.web.dto.organisation.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateOrganisationResponse {
    private final long id;
    private final String name;
    private final String address;
    private final String description;
}
