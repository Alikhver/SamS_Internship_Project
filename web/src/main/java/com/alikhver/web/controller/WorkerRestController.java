package com.alikhver.web.controller;

import com.alikhver.web.dto.worker.request.CreateWorkerRequest;
import com.alikhver.web.dto.worker.request.UpdateWorkerRequest;
import com.alikhver.web.dto.worker.response.CreateWorkerResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import com.alikhver.web.facade.WorkerFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workers")
@Validated
public class WorkerRestController {
    private final WorkerFacade workerFacade;

    @GetMapping("/{id}")
    @ApiOperation("Get Worker")
    public ResponseEntity<GetWorkerResponse> getWorker(@PathVariable Long id) {
        GetWorkerResponse response = workerFacade.getWorkerById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/")
    @ApiOperation("Create Worker")
    public ResponseEntity<CreateWorkerResponse> createWorker(@RequestBody @Validated CreateWorkerRequest request) {
        CreateWorkerResponse response = workerFacade.createWorker(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update Worker")
    public ResponseEntity<Void> updateWorker(@PathVariable Long id, @RequestBody @Validated UpdateWorkerRequest request) {
        workerFacade.updateWorker(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete Worker")
    public ResponseEntity<Long> deleteWorker(@PathVariable Long id) {
        workerFacade.deleteWorker(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
