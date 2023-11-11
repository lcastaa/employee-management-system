package xyz.aqlabs.ems.models.hr;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "hr_ticket")
@Entity
public class HrTicket {
    @Id
    @GeneratedValue
    private int id;
    private int employeeId;
    private String subject;
    private String message;
    private Date dateCreated;
    private Date dateClosed;
    private Boolean isResolved;
}
