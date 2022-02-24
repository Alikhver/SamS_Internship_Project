package com.alikhver.web.controller;

import com.alikhver.web.facade.WorkerFacade;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workers")
public class WorkerRestController {
    private WorkerFacade workerFacade;
}
