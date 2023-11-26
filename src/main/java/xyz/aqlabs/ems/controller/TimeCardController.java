package xyz.aqlabs.ems.controller;

// TimeCard Controller Handles http request made to the end point /api/v1/timecard

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.aqlabs.ems.models.queries.QueryDto;
import xyz.aqlabs.ems.models.timecard.TimeCardDto;
import xyz.aqlabs.ems.service.TimeCardService;

import java.util.Map;


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
    //@GetMapping
    //public ResponseEntity<?> getTimeCardByTimeParams(@RequestParam("employeeId") Integer employeeId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate){
    //    var dto = TimeCardDto.builder()
    //            .employeeId(employeeId)
    //            .startDate(startDate)
    //            .endDate(endDate)
    //            .build();
    //    return service.getTimeCardByEmployeeIdStartDateAndEndDate(dto);
    //}



    //returns time cards based a QueryDto
    @GetMapping()
    public ResponseEntity<?> getTimeCardsByParams(@RequestBody QueryDto dto){
        //if the request body is empty
        if(dto.getQuery().isEmpty())
            return ResponseEntity.badRequest().body(Map.of("Server Msg","Request is empty"));

        // If the request DTO is not empty and it exists
        // split the request by commas
        var dtoQueryArr = dto.getQuery().split(",");

        //DEBUG: to see query printed
        //for(String info: dtoQueryArr)
        //    System.out.println(info);


        //if the query ONLY contains for example [1,employeeId]
        //return ALL time cards containing the employeeId
        if(dtoQueryArr[1].equalsIgnoreCase("byemployeeid"))
            return service.getTimeCardsByEmployeeId(Integer.valueOf(dtoQueryArr[0]));


        //if the query contains for example[1, month, 06]
        //return ALL time cards containing the employeeId and the month
        if(dtoQueryArr[1].equalsIgnoreCase("bymonth"))
            return service.getTimeCardsByEmployeeIdAndMonth(0,0);


        //if the query contains for example [1, year, 2023]
        //return ALL the time cards containing the employeeId and the year
        if(dtoQueryArr[1].equalsIgnoreCase("byyear"))
            return service.getTimeCardsByEmployeeIdAndYear(0,0);


        //if the query contains for example [1,monthandyear, 06, 2023]
        //return ALL the time cards containing the employeeId, month and year
        if(dtoQueryArr[1].equalsIgnoreCase("bymonthyear"))
            return service.getTimeCardsByEmployeeIdMonthYear(0,0,0);


        return ResponseEntity.badRequest().body(Map.of("Server Msg: ","No query matched"));
    }



    //@GetMapping
    //public ResponseEntity<?> getTimeCardByEmployeeIdMonthYear(@RequestParam("employeeId") Integer employeeId, @RequestParam("month") String month,  @RequestParam("year") String year){

        //check if all the parameters are met if one is missing return a 5xx incomplete request error.
       // if(employeeId == null || employeeId <= 0)
       //     return ResponseEntity.badRequest().body(Map.of("Server Msg","EmployeeId MUST be Valid"));

       // if(month.isEmpty() || year.isEmpty())
       //     return ResponseEntity.badRequest().body(Map.of("Server Msg","Year or Month MUST be Valid"));

       // return ResponseEntity.ok().body(Map.of("Server Msg","Data passed is Valid"));
   // }


}
