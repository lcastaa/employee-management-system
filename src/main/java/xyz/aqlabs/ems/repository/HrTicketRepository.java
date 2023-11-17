package xyz.aqlabs.ems.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.aqlabs.ems.models.hr.HrTicket;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Optional;

@Repository
public interface HrTicketRepository extends JpaRepository<HrTicket, Integer> {


    // --------|Update, Delete Operations |--------
    @Modifying
    @Transactional
    @Query("UPDATE HrTicket ticket SET ticket.subject = :subject, ticket.message= :message, ticket.dateCreated = :dateCreated, ticket.dateClosed = :dateClosed, ticket.isResolved = :isResolved WHERE ticket.employee.id = :employeeId")
    void updateHrTicket(
                        @Param(value = "employeeId") Integer employeeId,
                        @Param(value = "subject") String subject,
                        @Param(value = "message") String message,
                        @Param(value = "dateCreated") Date dateCreated,
                        @Param(value = "dateClosed") Date dateClosed,
                        @Param(value = "isResolved") Boolean isResolved
                        );
    @Modifying @Transactional
    @Query("DELETE FROM HrTicket ticket WHERE ticket.employee.id = :employeeId")
    void deleteHrTicket(@Param(value = "employeeId") Integer employeeId);

    @NonNull
    @Query("SELECT ticket FROM HrTicket ticket WHERE ticket.employee.id = :employeeId AND ticket.isResolved = :isResolved")
    Optional<HrTicket[]> findByEmployeeIdAndBoolean(@Param("employeeId") Integer employeeId, @Param("isResolved") Boolean isResolved);




    //---------| Query Methods [Default] |--------
    @NonNull
    Optional<HrTicket> findById(Integer id);

}
