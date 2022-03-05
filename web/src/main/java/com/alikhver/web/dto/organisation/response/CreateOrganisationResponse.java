package com.alikhver.web.dto.organisation.response;

import lombok.Builder;

@Builder
public class CreateOrganisationResponse {
    private String name;
    private String address;
    private String description;
    //TODO Does a CreateOrganisationResponse need workers and utilities. On this stage lists are empty
//    private List<Worker> workers;
//    private List<Utility> utilities;
}
