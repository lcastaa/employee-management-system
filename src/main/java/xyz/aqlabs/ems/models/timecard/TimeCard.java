package xyz.aqlabs.ems.models.timecard;

import xyz.aqlabs.ems.models.employee.Employee;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "time_card")
public class TimeCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;  // Association to Employee

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @OneToMany(mappedBy = "timeCard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DayTimeCard> dayTimeCards;
}
