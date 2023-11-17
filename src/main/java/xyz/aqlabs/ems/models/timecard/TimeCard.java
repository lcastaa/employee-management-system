package xyz.aqlabs.ems.models.timecard;

// Represents a TimeCard model this is how it is also set up in the database

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.aqlabs.ems.models.employee.Employee;
import xyz.aqlabs.ems.models.punch.Punch;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "time_card")
public class TimeCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private Employee employee;

    private Date startDate;

    private Date endDate;

    @OneToMany(mappedBy = "timeCard", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Punch> punches;
}
