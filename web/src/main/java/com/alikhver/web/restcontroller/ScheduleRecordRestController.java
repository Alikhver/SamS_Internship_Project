package com.alikhver.web.restcontroller;

import com.alikhver.web.dto.record.request.CreateRecordRequest;
import com.alikhver.web.dto.record.response.GetRecordResponse;
import com.alikhver.web.facade.ScheduleRecordFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@RequestMapping("/records")
@Validated
public class ScheduleRecordRestController {
    public final ScheduleRecordFacade scheduleRecordFacade;

    @GetMapping("/{recordId}")
    @ApiOperation("Get ScheduleRecord")
    public ResponseEntity<GetRecordResponse> getRecord(@PathVariable @Positive Long recordId) {
        var response = scheduleRecordFacade.getRecord(recordId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/")
    @ApiOperation("Create Record")
    public ResponseEntity<Long> createRecord(@RequestBody @Validated CreateRecordRequest request) {
        Long requestId = scheduleRecordFacade.createRecord(request);

        return new ResponseEntity<>(requestId, HttpStatus.CREATED);
    }
}
