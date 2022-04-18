package com.alikhver.web.restcontroller;

import com.alikhver.web.dto.record.request.CancelRecordsRequest;
import com.alikhver.web.dto.record.request.CreateRecordRequest;
import com.alikhver.web.dto.record.request.CreateRecordsRequest;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PutMapping("/cancel")
    @ApiOperation("Cancel records of time range")
    public ResponseEntity<Void> cancelRecordsOfPeriod(@RequestBody @Validated CancelRecordsRequest request) {

        scheduleRecordFacade.cancelRecords(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create")
    @ApiOperation("Cancel records of time range")
    public ResponseEntity<Void> cancelRecordsOfPeriod(@RequestBody @Validated CreateRecordsRequest request) {

        scheduleRecordFacade.createRecords(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{recordId}/book")
    @ApiOperation("Assign Utility and Profile")
    public ResponseEntity<Void> assignUtilityAndProfile(@PathVariable @Positive Long recordId,
                                                        @RequestParam(name = "utility") @Positive Long utilityId,
                                                        @RequestParam(name = "profile") @Positive Long profileId) {

        scheduleRecordFacade.bookRecord(recordId, utilityId, profileId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{recordId}/release")
    @ApiOperation("Release ScheduleRecord(make available again)")
    public ResponseEntity<Void> releaseRecord(@PathVariable @Positive Long recordId) {

        scheduleRecordFacade.releaseRecord(recordId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{recordId}/cancel")
    @ApiOperation("Cancel ScheduleRecord")
    public ResponseEntity<Void> cancelRecord(@PathVariable @Positive Long recordId) {

        scheduleRecordFacade.cancelRecord(recordId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
