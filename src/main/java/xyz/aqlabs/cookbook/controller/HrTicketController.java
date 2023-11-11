package xyz.aqlabs.cookbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.aqlabs.cookbook.models.dto.HrTicketDto;
import xyz.aqlabs.cookbook.service.HrTicketService;

@RestController
@RequestMapping("/api/v1/hr")
public class HrTicketController {

    @Autowired
    private HrTicketService service;

    @PostMapping
    public ResponseEntity<?> createHrTicketController(@RequestBody HrTicketDto dto){
        return service.createHrTicket(dto);
    }

    @GetMapping
    public ResponseEntity<?> getListOfHrTicketsWithEmployeeIdAndBoolean(@RequestParam Integer employeeId, @RequestParam Boolean isResolved){
        return service.getListOfHrTicketsWithEmployeeIdAndBoolean(employeeId,isResolved);
    }

}
