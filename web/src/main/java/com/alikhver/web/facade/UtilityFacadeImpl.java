package com.alikhver.web.facade;

import com.alikhver.model.service.UtilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UtilityFacadeImpl implements UtilityFacade {
    private final UtilityService utilityService;
}
