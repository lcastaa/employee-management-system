package xyz.aqlabs.ems.controller;

// Punch Controller Handles http request made to the end point /api/v1/punch

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.aqlabs.ems.models.punch.PunchDto;
import xyz.aqlabs.ems.service.PunchService;

@RestController
@RequestMapping("/api/v1/punch")
@SuppressWarnings("unused")
public class PunchController {

    @Autowired
    private PunchService service;


    // Handles POST request to create a punch using PunchDto in the request body
    @PostMapping
    public ResponseEntity<?> createPunch(@RequestBody PunchDto dto){
        return service.createPunch(dto);
    }
}
