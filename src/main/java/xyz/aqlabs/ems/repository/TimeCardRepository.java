package xyz.aqlabs.ems.repository;

// Used to create communication between the time_card table in the database


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.aqlabs.ems.models.timecard.TimeCard;

import javax.transaction.Transactional;
import java.util.*;


@Repository
public interface TimeCardRepository extends JpaRepository<TimeCard,Integer> {


    @Transactional @Query("SELECT t FROM TimeCard t WHERE t.employee.id = :employeeId AND t.startDate = :startDate AND t.endDate = :endDate")
    Optional<TimeCard> findByEmployeeIdAndStartDateAndEndDate(@Param("employeeId") Integer employeeId,
                                                              @Param("startDate") Date startDate,
                                                              @Param("endDate") Date endDate);



}