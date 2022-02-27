package com.alikhver.web.controller;

import com.alikhver.web.facade.UtilityFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/utilities")
public class UtilityRestController {
    private final UtilityFacade utilityFacade;
}
