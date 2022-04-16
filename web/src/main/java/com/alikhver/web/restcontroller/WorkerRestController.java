package com.alikhver.web.restcontroller;

import com.alikhver.web.dto.record.response.GetRecordResponse;
import com.alikhver.web.dto.utility.response.GetUtilityResponse;
import com.alikhver.web.dto.worker.request.CreateWorkerRequest;
import com.alikhver.web.dto.worker.request.UpdateWorkerRequest;
import com.alikhver.web.dto.worker.response.CreateWorkerResponse;
import com.alikhver.web.dto.worker.response.GetWorkerResponse;
import com.alikhver.web.facade.ScheduleRecordFacade;
import com.alikhver.web.facade.WorkerFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workers")
@Validated
public class WorkerRestController {
    private final WorkerFacade workerFacade;
    private final ScheduleRecordFacade scheduleRecordFacade;

    @GetMapping("/{id}")
    @ApiOperation("Get Worker")
    public ResponseEntity<GetWorkerResponse> getWorker(@PathVariable Long id) {
        GetWorkerResponse response = workerFacade.getWorkerById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/utilities")
    @ApiOperation("Get Utilities of Worker")
    public ResponseEntity<Page<GetUtilityResponse>> getWorkersOfUtility(@PathVariable @Positive Long id,
                                                                        @RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                                                        @RequestParam(defaultValue = "5") @Positive int size) {
        var response = workerFacade.getUtilitiesOfWorker(id, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/futureRecords")
    @ApiOperation("Get All Future Records")
    public ResponseEntity<List<GetRecordResponse>> getRecordsOfWorker(@PathVariable(name = "id") @Positive Long workerId) {

        var response = workerFacade.getAllFutureRecords(workerId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Create Worker")
    public ResponseEntity<CreateWorkerResponse> createWorker(@RequestBody @Validated CreateWorkerRequest request) {
        CreateWorkerResponse response = workerFacade.createWorker(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/addUtility/{utilityId}")
    @ApiOperation("Add Utility to Worker")
    public ResponseEntity<Void> addUtilityToWorker(@PathVariable @Positive Long id,
                                                   @PathVariable @Positive Long utilityId) {
        workerFacade.addUtility(id, utilityId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/removeUtility/{utilityId}")
    @ApiOperation("Delete Utility from Worker")
    public ResponseEntity<Void> removeUtilityFromWorker(@PathVariable @Positive Long id,
                                                        @PathVariable @Positive Long utilityId) {
        workerFacade.deleteUtility(id, utilityId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
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
