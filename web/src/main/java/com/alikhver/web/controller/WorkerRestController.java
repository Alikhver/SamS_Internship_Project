package com.alikhver.web.controller;

import com.alikhver.web.dto.worker.request.CreateWorkerRequest;
import com.alikhver.web.dto.worker.response.CreateWorkerResponse;
import com.alikhver.web.facade.WorkerFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workers")
public class WorkerRestController {
    private final WorkerFacade workerFacade;

    @PostMapping("/")
    @ApiOperation("Create Worker")
    public ResponseEntity<CreateWorkerResponse> createWorker(@RequestBody @Validated CreateWorkerRequest request) {
        CreateWorkerResponse response = workerFacade.createWorker(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
