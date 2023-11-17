package xyz.aqlabs.ems.service;


/*
This Service Class is responsible for handling the business
logic of any employee query made from the controller.
 */


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.aqlabs.ems.models.employee.Employee;
import xyz.aqlabs.ems.models.employee.EmployeeDto;
import xyz.aqlabs.ems.repository.EmployeeRepository;
import xyz.aqlabs.ems.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;

@Service
@RequiredArgsConstructor
public class EmployeeService  {


    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);
    @Autowired
    private final EmployeeRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    @PersistenceContext
    private EntityManager entityManager;

    public ResponseEntity<?> createEmployee(EmployeeDto dto) {
        // Logs when method is invoked provides hash code for following object through log file.
        LOGGER.info("METHOD EXECUTING IN EMPLOYEE SERVICE: createEmployee("+dto.hashCode()+") ");

        var employee = Employee.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .build();

        // Repository saves the entity to the database.
        repo.save(employee);
        LOGGER.info("Employee with hashcode: "+employee.hashCode()+" has been CREATED");
        var token = jwtService.generateToken(employee);
        LOGGER.info("METHOD EXITING: createEmployee("+dto.hashCode()+")"+ "\n");
        return ResponseEntity.ok().body("Username: "+dto.getUsername()+"\n"+"Password: "+dto.getPassword());
    }

    @Transactional
    public ResponseEntity<?> updateEmployee(Employee employee){
        // Logs when method is invoked provides hash code for following object through log file.
        LOGGER.info("METHOD EXECUTING in EMPLOYEE SERVICE: updateEmployee("+employee.hashCode()+")");

        // If user is valid but user does not exist via its ID , return 404 not found.
        LOGGER.info("CHECKING if Employee with employeeID: "+ employee.getEmployeeId()+ " exists.");
        if(!repo.existsById(employee.getEmployeeId())) {
            LOGGER.info("Employee with employeeID: " + employee.getEmployeeId() + " DID NOT exist.");
            LOGGER.info("METHOD EXITING: updateEmployee("+employee.hashCode()+")  with 404 NOT FOUND" + "\n");
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body("Employee with employeeId: " + employee.getEmployeeId() + " was NOT FOUND.");
        }

        // if the user is valid and user exists via ID, update the user, and return 200 ok.
        LOGGER.info("Employee with employeeId: " + employee.getEmployeeId() + " DOES EXIST.");
        LOGGER.info("Applying changes to Employee...");
        repo.updateEmployee(
                employee.getEmployeeId(),
                employee.getUsername(),
                employee.getEmail()
        );
        LOGGER.info("Employee with employeeID " + employee.getEmployeeId() + " was CHANGED.");
        LOGGER.info("METHOD EXITING: updateEmployee("+employee.hashCode()+")"+"\n");
        return ResponseEntity.ok().body("Employee Updated");
    }

    @Transactional
    public ResponseEntity<?> deleteEmployee(Integer employeeId) {
        // Logs when method is invoked provides hash code for following object through log file.
        LOGGER.info("METHOD EXECUTING in EMPLOYEE SERVICE: deleteEmployee("+employeeId+")");

        // Checks to see if User exists in the returns 404 NOT-FOUND
        LOGGER.info("CHECKING if Employee exists with employeeId: "+employeeId+".");
        if(!repo.existsById(employeeId)) {
            LOGGER.info("Employee with employeeId: " + employeeId + " did NOT EXIST.");
            LOGGER.info("METHOD EXITING: deleteUser("+employeeId+") with a 404 NOT FOUND"+"\n");
            return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body("No Employee with employeeId: " + employeeId+" was found.");
        }

        // Repository deletes User entity with matching ID returns 203 NO-CONTENT
        LOGGER.info("Employee with employeeId: " + employeeId + " did EXIST.");
        LOGGER.info("DELETING User...");
        repo.deleteEmployee(employeeId);
        LOGGER.info("METHOD EXITING: deleteUser("+employeeId+") with 204 - SUCCESS" + "\n");
        return  ResponseEntity.status(HttpStatus.NO_CONTENT.value()).body("DELETED Employee with employeeId:"+ employeeId);
    }

    public ResponseEntity<?> getEmployeeByEmployeeId(Integer employeeId){
        // Logs when method is invoked provides hash code for following object through log file.
        LOGGER.info("METHOD EXECUTING in EMPLOYEE SERVICE: getEmployeeByEmployeeId("+employeeId+") |---[o][o][o]");


        LOGGER.info("CHECKING if Employee with employeeId: "+employeeId+" EXISTS...");
        //makes sure the response is not empty
        LOGGER.info("Making sure RESPONSE is not empty...");
        if(repo.findById(employeeId).isPresent()){

            LOGGER.info("Employee EXISTS and Respone is valid...");
            var response = repo.findById(employeeId);
            var user = response.orElseThrow();
            LOGGER.info("METHOD EXITING: deleteUser("+employeeId+") with a 200 OK" + "\n");
            return ResponseEntity.ok(user);
        }
        LOGGER.info("Employee with employeeId: "+employeeId+" did NOT EXIST.");
        LOGGER.info("METHOD EXITING: deleteEmployee("+employeeId+") with a 404 NOT FOUND" + "\n");
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body("Employee with employeeId: "+employeeId+" WAS NOT FOUND");
    }

    public ResponseEntity<?> getEmployeeByUsername(String username){
        // Logs when method is invoked provides hash code for following object through log file.
        LOGGER.info("METHOD EXECUTING in EMPLOYEE SERVICE: getEmployeeByUsername("+username+") |---[o][o][o]");


        LOGGER.info("CHECKING if Employee with username: "+username+" EXISTS...");
        //makes sure the response is not empty
        LOGGER.info("Making sure RESPONSE is valid...");
        if(repo.findByUsername(username).isPresent()){

            LOGGER.info("Employee with username: "+username+" EXISTS and Response is VALID");
            var response = repo.findByUsername(username);
            var user = response.orElseThrow();
            LOGGER.info("METHOD EXITING: getEmployeeByUsername("+ username +") with a 200 OK" + "\n");
            return ResponseEntity.ok(user);
        }
        LOGGER.info("Employee with username: "+username+" did NOT EXIST.");
        LOGGER.info("METHOD EXITING: getEmployeeByUsername("+username+") with a 404 NOT FOUND"+ "\n");
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body("Employee with username: "+username+" WAS NOT FOUND");
    }


}
