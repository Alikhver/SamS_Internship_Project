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
    //TODO Does a CreateOrganisationResponse need workers and utilities. On this stage lists are empty
//    private List<Worker> workers;
//    private List<Utility> utilities;
}
