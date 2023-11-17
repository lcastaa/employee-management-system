package xyz.aqlabs.ems.controller;

// TimeCard Controller Handles http request made to the end point /api/v1/timecard

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.aqlabs.ems.models.timecard.TimeCardDto;
import xyz.aqlabs.ems.service.TimeCardService;


@RestController
@RequestMapping("/api/v1/timecard")
@SuppressWarnings("unused")
public class TimeCardController {


    @Autowired
    private TimeCardService service;

    // Handles POST request to create a timeCard using TimeCardDto in the request body
    @PostMapping
    public ResponseEntity<?> createTimeCard(@RequestBody TimeCardDto dto){
        return service.createTimeCard(dto);
    }


    // Handles GET requests to find timeCards belonging to the employee using employeeId, startDate, and endate
    @GetMapping
    public ResponseEntity<?> getTimeCardByTimeParams(@RequestParam("employeeId") Integer employeeId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate){
        var dto = TimeCardDto.builder()
                .employeeId(employeeId)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        return service.getTimeCardByEmployeeIdStartDateAndEndDate(dto);
    }


}
