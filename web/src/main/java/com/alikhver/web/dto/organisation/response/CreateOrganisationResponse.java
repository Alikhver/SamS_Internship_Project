package com.alikhver.web.dto.organisation.response;

import com.alikhver.model.entity.Utility;
import com.alikhver.model.entity.Worker;
import lombok.Builder;

import java.util.List;

@Builder
public class CreateOrganisationResponse {
    private String name;
    private String address;
    private String description;
    private List<Worker> workers;
    private List<Utility> utilities;
}
