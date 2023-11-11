package xyz.aqlabs.ems.models.punch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.aqlabs.ems.models.timecard.DayTimeCard;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "punch")
public class Punch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("status")
    private String status;

    @JsonProperty("timestamp")
    @Column(name = "time_stamp")
    private Time timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_time_card_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private DayTimeCard dayTimeCard;
}
