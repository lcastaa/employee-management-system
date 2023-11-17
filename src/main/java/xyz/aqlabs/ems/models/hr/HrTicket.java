package xyz.aqlabs.ems.models.hr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.aqlabs.ems.models.employee.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hr_ticket")
public class HrTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Employee employee;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("message")
    private String message;

    @JsonProperty("dateCreated")
    @Column(name = "date_created")
    private Date dateCreated;

    @JsonProperty("dateClosed")
    @Column(name = "date_closed")
    private Date dateClosed;

    @JsonProperty("isResolved")
    @Column(name = "is_resolved")
    private Boolean isResolved;
}
