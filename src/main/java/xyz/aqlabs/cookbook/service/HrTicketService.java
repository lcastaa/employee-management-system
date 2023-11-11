package xyz.aqlabs.cookbook.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.aqlabs.cookbook.models.HrTicket;
import xyz.aqlabs.cookbook.models.dto.HrTicketDto;
import xyz.aqlabs.cookbook.repository.HrTicketRepository;

import javax.persistence.criteria.CriteriaBuilder;

@Service
@RequiredArgsConstructor
public class HrTicketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HrTicketService.class);
    @Autowired
    private final HrTicketRepository repo;

    public ResponseEntity<?> createHrTicket(HrTicketDto dto) {
        // Logs when method is invoked provides hash code for following object through log file.
        LOGGER.info("METHOD EXECUTING IN HR-TICKET SERVICE: createHrTicket("+dto.hashCode()+") ");

        var ticket = HrTicket.builder()
                .employeeId(dto.getEmployeeId())
                .subject(dto.getSubject())
                .message(dto.getMessage())
                .dateCreated(dto.getDateCreated())
                .dateClosed(dto.getDateClosed())
                .isResolved(dto.getIsResolved())
                .build();

        // Repository saves the entity to the database.
        repo.save(ticket);
        LOGGER.info("Ticket with hashcode: "+ticket.hashCode()+" has been CREATED");
        LOGGER.info("METHOD EXITING: createHrTicket("+dto.hashCode()+")"+ "\n");
        return ResponseEntity.accepted().build();
    }

    public ResponseEntity<?> getListOfHrTicketsWithEmployeeIdAndBoolean(@RequestParam Integer employeeId, @RequestParam Boolean isResolved) {
        // Logs when method is invoked provides hash code for following object through log file.
        LOGGER.info("METHOD EXECUTING IN HR-TICKET SERVICE:  getListOfHrTicketsWithEmployeeIdAndBoolean(" + employeeId + ", " + isResolved + ")");
        LOGGER.info("Checking if Tickets exist");
        var tickets = repo.findByEmployeeIdAndBoolean(employeeId, isResolved);

        LOGGER.info("Checking if response is valid");
        if (tickets.isPresent()){
            LOGGER.info("METHOD Exiting: getListOfHrTicketsWithEmployeeIdAndBoolean(" + employeeId + ", " + isResolved + ") with 200 OK"+"\n");
        return ResponseEntity.ok().body(tickets.get());
        }
        LOGGER.info("METHOD Exiting: getListOfHrTicketsWithEmployeeIdAndBoolean("+employeeId+", "+ isResolved+ ") with 404 Not Found"+"\n");
        return ResponseEntity.notFound().build();
    }

}
