package xyz.aqlabs.ems.service;

// Handles the logic of all operations made on Punches

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xyz.aqlabs.ems.models.punch.Punch;
import xyz.aqlabs.ems.models.punch.PunchDto;
import xyz.aqlabs.ems.models.timecard.TimeCard;
import xyz.aqlabs.ems.repository.PunchRepository;
import xyz.aqlabs.ems.repository.TimeCardRepository;

import java.sql.Date;
import java.sql.Time;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PunchService {


    // creates a logger for this class
    private static final Logger LOGGER = LoggerFactory.getLogger(PunchService.class);
    @Autowired
    PunchRepository punchRepository;
    @Autowired
    TimeCardRepository timeCardRepository;


    // Creates Punch using the PunchDto.
    public ResponseEntity<?> createPunch(PunchDto dto){
        LOGGER.info("METHOD EXECUTING IN [PUNCH SERVICE]: createPunch(" + dto.hashCode() + ") ");

        TimeCard timeCard = timeCardRepository.findById(dto.getTimeCardId())
                .orElseThrow(() -> new RuntimeException("TimeCard not found..."));

        LOGGER.info("Building the Punch Object...");

        var punch = Punch.builder()
                .timeCard(timeCard)
                .date(Date.valueOf(dto.getDate()))
                .timestamp(Time.valueOf(dto.getTimestamp()))
                .status(dto.getStatus())
                .build();

        LOGGER.info("Punch Built! Saving Punch to DB...");

        punchRepository.save(punch);

        LOGGER.info("Punch successfully saved...");
        LOGGER.info("METHOD EXITING: createPunch("+dto.hashCode()+")  with 200 OK" + "\n");

        return ResponseEntity.accepted().body(Map.of("Server Msg: ","Punch Submitted"));
    }

}
