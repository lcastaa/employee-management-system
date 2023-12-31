package xyz.aqlabs.cookbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.aqlabs.cookbook.models.dto.EmployeeDto;
import xyz.aqlabs.cookbook.models.dto.LoginDto;
import xyz.aqlabs.cookbook.security.AuthenticationService;
import xyz.aqlabs.cookbook.service.EmployeeService;


@RestController
@RequestMapping("/api/v1/authenticate")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationService authService;
    private final EmployeeService service;


    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody EmployeeDto dto){return service.createEmployee(dto);}


    @PostMapping("/login")
    ResponseEntity<?> logIn(@RequestBody LoginDto dto){return authService.logIn(dto);}


}
