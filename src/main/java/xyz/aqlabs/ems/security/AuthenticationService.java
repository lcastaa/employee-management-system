package xyz.aqlabs.ems.security;

/*
This Class is responsible for authenticating the employee
using the information from front-end login pages.
*/


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.aqlabs.ems.models.authentication.LoginDto;
import xyz.aqlabs.ems.models.authentication.ResponseDto;
import xyz.aqlabs.ems.repository.EmployeeRepository;

import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class AuthenticationService{


    @Autowired
    private final EmployeeRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    @PersistenceContext
    private EntityManager entityManager;


    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);


    //------| Authenticates employee|---->
    public ResponseEntity<?> logIn(LoginDto dto) {
        LOGGER.info("METHOD EXECUTING in AUTHENTICATION SERVICE: logIn("+ dto.hashCode() +")");
        LOGGER.info("Attempting to VALIDATE credentials in dto: "+ dto.hashCode()+".");
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        }catch (AuthenticationException e){
            LOGGER.info("AUTHENTICATION FAILED with dto: "+ dto.hashCode() +"." + "\n");
            return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body("Server Authentication FAILED");
        }

        LOGGER.info("CHECKING if Employee with username: "+ dto.getUsername() +" exists.");
        try {
            var employee = repo.findByUsername(dto.getUsername())
                    .orElseThrow();
            var token = jwtService.generateToken(employee);
            LOGGER.info("AUTHENTICATION PASSED" + "\n");
            return ResponseEntity.ok().body(new ResponseDto(employee,token));

        }catch (NoSuchElementException e){
            LOGGER.info("AUTHENTICATION FAILED");
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body("Invalid Login Credentials");
        }
    }
}