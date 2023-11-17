package xyz.aqlabs.ems.models.hr;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HrTicketDto {
    private int employeeId;
    private String subject;
    private String message;
    private Date dateCreated;
    private Date dateClosed;
    private Boolean isResolved;
}
