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


    //finds a single timeCard based on the employeeId, startDate and endDate
    @Transactional @Query("SELECT t FROM TimeCard t WHERE t.employee.id = :employeeId AND t.startDate = :startDate AND t.endDate = :endDate")
    Optional<TimeCard> findByEmployeeIdAndStartDateAndEndDate(
            @Param("employeeId") int employeeId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    //finds a list of employee's timeCards based on the employeeId and month
    @Transactional @Query(value = "SELECT * FROM time_card WHERE extract(Month from start_date) = :month AND employee_id = :employeeId", nativeQuery = true)
    Optional<TimeCard[]> retrieveTimeCardsUsingMonth(
            @Param("employeeId") int employeeId,
            @Param("month") int month
    );


    // finds a list of employee's timeCards based on the employeeId and year
    //TODO - Need to find the SQL statement to bring back TimeCards based on the employeeId and Year
    @Transactional @Query(value = "SELECT * FROM time_card WHERE extract(Year from start_date) = :year AND employee_id = :employeeId ", nativeQuery = true)
    Optional<TimeCard[]> retrieveTimeCardsUsingIdAndYear(
            @Param("employeeId") int employeeId,
            @Param("year") int year
    );


    // finds a list of employee's timeCards based on the employeeId, month, and year
    //TODO - Need to find the SQL statement to bring back TimeCards based on the employeeId and Month and Year
    @Transactional @Query(value = "SELECT * FROM time_card WHERE extract(Year from start_date) = :year AND extract(Month from start_date) = :month AND  employee_id = :employeeId ", nativeQuery = true)
    Optional<TimeCard[]> retrieveTimeCardsUsingIdAndMonthAndYear(
            @Param("employeeId") Integer employeeId,
            @Param("month") int month,
            @Param("year") int year
    );
}
