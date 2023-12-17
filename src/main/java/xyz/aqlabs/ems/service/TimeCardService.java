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
    public ResponseEntity<?> createTimeCard(Integer employeeId, String startDate, String endDate) {
        LOGGER.info("METHOD EXECUTING: createTimeCard()");
        LOGGER.info("Checking if TimeCard Object already exists with data...");
        Optional<TimeCard> existingTimeCard = timeCardRepository.findByEmployeeIdAndStartDateAndEndDate(employeeId, Date.valueOf(startDate), Date.valueOf(endDate));


        if (existingTimeCard.isPresent()) {
            LOGGER.info("TimeCard already did exist...");
            LOGGER.info("METHOD EXITING: createTimeCard() with 525 Resource Exists " + "\n");
            return ResponseEntity.status(525).body(Map.of("Message", "This TimeCard Exists"));
        }
        else {
            LOGGER.info("TimeCard Object was not found...");
            LOGGER.info("Building the TimeCard Object...");

            TimeCard timeCard = buildTimeCard(employeeId, startDate, endDate);

            LOGGER.info("Time Card Built! Saving Time Card to DB...");

            timeCardRepository.save(timeCard);

            LOGGER.info("Time Card successfully saved...");
            LOGGER.info("METHOD EXITING: createTimeCard()  with 204 ACCEPTED" + "\n");
            return ResponseEntity.accepted().build();
        }
    }

    // Responsible for getting TimeCard By week
    public ResponseEntity<?> getTimeCardByEmployeeIdStartDateAndEndDate(Integer employeeId, String startDate, String endDate) {

        LOGGER.info("METHOD EXECUTING: getTimeCardByEmployeeIdStartDateAndEndDate()");

        Optional<TimeCard> existingTimeCard = timeCardRepository.findByEmployeeIdAndStartDateAndEndDate(employeeId, Date.valueOf(startDate), Date.valueOf(endDate));

        if (existingTimeCard.isPresent()) {

            LOGGER.info("Time Card found...Returning existing Time Card");

            return ResponseEntity.ok(existingTimeCard.get());
        }
        else {

            return createTimeCard(employeeId, startDate, endDate);

        }
    }



    //TODO -- Implement the logic and input data validation and handle any errors thrown by the logic
    //public ResponseEntity<?> getTimeCardsByEmployeeId(Integer employeeId){
    //    return ResponseEntity.ok("");
    //}


    //TODO -- Implement the logic and input data validation and handle any errors thrown by the logic
    public ResponseEntity<?> retrieveTimeCardsUsingMonth(Integer employeeId, Integer month){
       var timeCardArr = timeCardRepository.retrieveTimeCardsUsingMonth(employeeId,month);
       return ResponseEntity.ok(timeCardArr.get());
    }

    //TODO -- Implement the logic and input data validation and handle any errors thrown by the logic
    public ResponseEntity<?> retrieveTimeCardsUsingEmployeeIdAndYear(Integer employeeId, Integer year){
        var timeCardArr = timeCardRepository.retrieveTimeCardsUsingIdAndYear(employeeId, year);
        return ResponseEntity.ok(timeCardArr.get());
    }

    //TODO -- Implement the logic and input data validation and handle any errors thrown by the logic
    public ResponseEntity<?> retrieveTimeCardsByEmployeeIdAndMonthYear(Integer employeeId, Integer month, Integer year){
        var timeCardArr = timeCardRepository.retrieveTimeCardsUsingIdAndMonthAndYear(employeeId,month,year);
        return ResponseEntity.ok(timeCardArr.get());
    }



    // Helper method to build a TimeCard
    private TimeCard buildTimeCard(Integer employeeId, String startDate, String endDate) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return TimeCard.builder()
                .employee(employee)
                .startDate(Date.valueOf(startDate))
                .endDate(Date.valueOf(endDate))
                .punches(new ArrayList<>())
                .build();
    }

}