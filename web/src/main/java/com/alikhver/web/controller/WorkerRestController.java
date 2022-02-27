package com.alikhver.web.controller;

import com.alikhver.web.facade.WorkerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workers")
public class WorkerRestController {
    private final WorkerFacade workerFacade;
}
