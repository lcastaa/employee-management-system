package xyz.aqlabs.ems.models.punch;

import xyz.aqlabs.ems.models.timecard.DayTimeCard;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "punch")
public class Punch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String status;

    @Column(name = "time_stamp")
    private Date timeStamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_time_card_id")
    private DayTimeCard dayTimeCard;

    // Constructors, getters, and setters
}
