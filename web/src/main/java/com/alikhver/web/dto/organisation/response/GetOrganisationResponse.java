package com.alikhver.web.dto.organisation.response;

import com.alikhver.model.entity.Utility;
import com.alikhver.model.entity.Worker;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class GetOrganisationResponse {
    private final long id;
    private final String name;
    private final String address;
    private final String description;
    private final boolean isActive;
    private final List<Worker> workers;
    private final List<Utility> utilities;
}
