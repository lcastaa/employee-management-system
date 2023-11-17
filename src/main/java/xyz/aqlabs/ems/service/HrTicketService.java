package xyz.aqlabs.ems.service;

// Handles the logic of all operations made on Hr-Tickets

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xyz.aqlabs.ems.models.hr.HrTicket;
import xyz.aqlabs.ems.models.hr.HrTicketDto;
import xyz.aqlabs.ems.repository.HrTicketRepository;
import xyz.aqlabs.ems.repository.EmployeeRepository;
import xyz.aqlabs.ems.models.employee.Employee;

@Service
@RequiredArgsConstructor
public class HrTicketService {


    // creates a logger for this class
    private static final Logger LOGGER = LoggerFactory.getLogger(HrTicketService.class);

    @Autowired
    HrTicketRepository hrTicketRepository;

    @Autowired
    EmployeeRepository employeeRepository;


    // Creates Hr-Tickets using the HrTicketDto.
    public ResponseEntity<?> createHrTicket(HrTicketDto dto) {
        LOGGER.info("METHOD EXECUTING IN HR-TICKET SERVICE: createHrTicket(" + dto.hashCode() + ") ");

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LOGGER.info("Building the Hr-Ticket Object...");

        HrTicket ticket = HrTicket.builder()
                .dateClosed(dto.getDateClosed())
                .dateCreated(dto.getDateCreated())
                .employee(employee)
                .isResolved(dto.getIsResolved())
                .message(dto.getMessage())
                .subject(dto.getSubject())
                .build();
        LOGGER.info("Hr-Tickt Built! Saving Hr-Ticket to DB...");

        hrTicketRepository.save(ticket);

        LOGGER.info("Hr-Ticket successfully saved...");
        LOGGER.info("METHOD EXITING: createPunch("+dto.hashCode()+")  with 201 OK" + "\n");

        return ResponseEntity.accepted().build();
    }


    // Gets a List of Hr-Tickets based on employeeId and the resolve status.
    public ResponseEntity<?> getListOfHrTicketsWithEmployeeIdAndBoolean(Integer employeeId, Boolean isResolved) {

        LOGGER.info("METHOD EXECUTING IN HR-TICKET SERVICE: getListOfHrTicketsWithEmployeeIdAndBoolean(" + employeeId + ", " + isResolved + ")");

        var tickets = hrTicketRepository.findByEmployeeIdAndBoolean(employeeId, isResolved);

        // returns a 200 ok if it's present.
        if (tickets.isPresent()) {

            LOGGER.info("METHOD Exiting: getListOfHrTicketsWithEmployeeIdAndBoolean(" + employeeId + ", " + isResolved + ") with 200 OK" + "\n");

            return ResponseEntity.ok().body(tickets.get());
        }

        // returns a 404 not-found if it's not present.

        LOGGER.info("METHOD Exiting: getListOfHrTicketsWithEmployeeIdAndBoolean(" + employeeId + ", " + isResolved + ") with 404 Not Found" + "\n");

        return ResponseEntity.notFound().build();
    }
}
