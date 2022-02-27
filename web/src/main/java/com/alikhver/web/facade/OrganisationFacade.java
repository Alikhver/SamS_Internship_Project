package com.alikhver.web.facade;

import com.alikhver.model.service.OrganisationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganisationFacade {
    private final OrganisationService organisationService;
}
