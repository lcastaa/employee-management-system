package xyz.aqlabs.ems.service;

// Handles the logic of all operations made on TimeCards


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xyz.aqlabs.ems.models.employee.Employee;
import xyz.aqlabs.ems.models.timecard.TimeCard;
import xyz.aqlabs.ems.models.timecard.TimeCardDto;
import xyz.aqlabs.ems.repository.EmployeeRepository;
import xyz.aqlabs.ems.repository.TimeCardRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimeCardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeCardService.class);

    @Autowired
    private TimeCardRepository timeCardRepository;
    @Autowired
    private EmployeeRepository employeeRepository;


    // Responsible for creating TimeCards
    public ResponseEntity<?> createTimeCard(TimeCardDto dto) {
        LOGGER.info("METHOD EXECUTING: createTimeCard(" + dto.hashCode() + ")");
        LOGGER.info("Checking if TimeCard Object already exists with Dto data...");
        Optional<TimeCard> existingTimeCard = timeCardRepository.findByEmployeeIdAndStartDateAndEndDate(dto.getEmployeeId(), Date.valueOf(dto.getStartDate()), Date.valueOf(dto.getEndDate()));


        if (existingTimeCard.isPresent()) {
            LOGGER.info("TimeCard already did exist...");
            LOGGER.info("METHOD EXITING: createTimeCard("+dto.hashCode()+")  with 525 Resource Exists " + "\n");
            return ResponseEntity.status(525).body(Map.of("Message", "This TimeCard Exists"));
        }
        else {
            LOGGER.info("TimeCard Object was not found...");
            LOGGER.info("Building the TimeCard Object...");

            TimeCard timeCard = buildTimeCard(dto);

            LOGGER.info("Time Card Built! Saving Time Card to DB...");

            timeCardRepository.save(timeCard);

            LOGGER.info("Time Card successfully saved...");
            LOGGER.info("METHOD EXITING: createTimeCard("+dto.hashCode()+")  with 204 ACCEPTED" + "\n");
            return ResponseEntity.accepted().build();
        }
    }


    // Responsible for getting TimeCards
    public ResponseEntity<?> getTimeCardByEmployeeIdStartDateAndEndDate(TimeCardDto dto) {

        LOGGER.info("METHOD EXECUTING: getTimeCardByEmployeeIdStartDateAndEndDate(" + dto.hashCode() + ")");

        Optional<TimeCard> existingTimeCard = timeCardRepository.findByEmployeeIdAndStartDateAndEndDate(dto.getEmployeeId(), Date.valueOf(dto.getStartDate()), Date.valueOf(dto.getEndDate()));

        if (existingTimeCard.isPresent()) {

            LOGGER.info("Time Card found...Returning existing Time Card");

            return ResponseEntity.ok(existingTimeCard.get());
        }
        else {

            return createTimeCard(dto);

        }
    }


    //TODO -- Implement the logic and input data validation and handle any errors thrown by the logic
    public ResponseEntity<?> getTimeCardsByEmployeeId(Integer employeeId){
        return ResponseEntity.ok("");
    }

    //TODO -- Implement the logic and input data validation and handle any errors thrown by the logic
    public ResponseEntity<?> getTimeCardsByEmployeeIdAndMonth(Integer employeeId, Integer month){
        return ResponseEntity.ok("");
    }

    //TODO -- Implement the logic and input data validation and handle any errors thrown by the logic
    public ResponseEntity<?> getTimeCardsByEmployeeIdAndYear(Integer employeeId, Integer year){
        return ResponseEntity.ok("");
    }

    //TODO -- Implement the logic and input data validation and handle any errors thrown by the logic
    public ResponseEntity<?> getTimeCardsByEmployeeIdMonthYear(Integer employeeId, Integer month, Integer year){
        return ResponseEntity.ok("");
    }

    // Helper method to build a TimeCard
    private TimeCard buildTimeCard(TimeCardDto dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return TimeCard.builder()
                .employee(employee)
                .startDate(Date.valueOf(dto.getStartDate()))
                .endDate(Date.valueOf(dto.getEndDate()))
                .punches(new ArrayList<>())
                .build();
    }
}