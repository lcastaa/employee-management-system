package xyz.aqlabs.ems.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xyz.aqlabs.ems.models.hr.HrTicket;
import xyz.aqlabs.ems.models.hr.HrTicketDto;
import xyz.aqlabs.ems.repository.HrTicketRepository;
import xyz.aqlabs.ems.repository.EmployeeRepository;
import xyz.aqlabs.ems.models.employee.Employee;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class HrTicketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HrTicketService.class);
    private final HrTicketRepository hrTicketRepository;
    private final EmployeeRepository employeeRepository;

    public ResponseEntity<?> createHrTicket(HrTicketDto dto) {
        LOGGER.info("METHOD EXECUTING IN HR-TICKET SERVICE: createHrTicket(" + dto.hashCode() + ") ");

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found")); // Exception handling as per your need

        HrTicket ticket = HrTicket.builder()
                .dateClosed(dto.getDateClosed())
                .dateCreated(dto.getDateCreated())
                .employee(employee)
                .isResolved(dto.getIsResolved())
                .message(dto.getMessage())
                .subject(dto.getSubject())
                .build();

        hrTicketRepository.save(ticket);
        LOGGER.info("Ticket with hashcode: " + ticket.hashCode() + " has been CREATED");
        LOGGER.info("METHOD EXITING: createHrTicket(" + dto.hashCode() + ")" + "\n");
        return ResponseEntity.accepted().body(Map.of("Server Message","Accepted"));
    }

    public ResponseEntity<?> getListOfHrTicketsWithEmployeeIdAndBoolean(Integer employeeId, Boolean isResolved) {
        LOGGER.info("METHOD EXECUTING IN HR-TICKET SERVICE: getListOfHrTicketsWithEmployeeIdAndBoolean(" + employeeId + ", " + isResolved + ")");
        var tickets = hrTicketRepository.findByEmployeeIdAndBoolean(employeeId, isResolved);

        if (tickets.isPresent()) {
            LOGGER.info("METHOD Exiting: getListOfHrTicketsWithEmployeeIdAndBoolean(" + employeeId + ", " + isResolved + ") with 200 OK" + "\n");
            return ResponseEntity.ok().body(tickets.get());
        }
        LOGGER.info("METHOD Exiting: getListOfHrTicketsWithEmployeeIdAndBoolean(" + employeeId + ", " + isResolved + ") with 404 Not Found" + "\n");
        return ResponseEntity.notFound().build();
    }
}
