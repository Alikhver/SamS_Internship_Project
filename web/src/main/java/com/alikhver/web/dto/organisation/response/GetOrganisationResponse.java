package com.alikhver.web.dto.organisation.response;

import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
public class GetOrganisationResponse {
    private final long id;
    private final String name;
    private final String address;
    private final String description;
    private final boolean isActive;
    private final Date dateCreated;
    private final List<GetWorkerResponse> workers;
    private final List<GetUtilityResponse> utilities;
}
