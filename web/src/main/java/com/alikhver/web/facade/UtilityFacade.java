package com.alikhver.web.facade;

import com.alikhver.model.service.UtilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilityFacade {
    private final UtilityService utilityService;
}
