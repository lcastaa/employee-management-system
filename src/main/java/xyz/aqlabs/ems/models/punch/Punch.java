package xyz.aqlabs.ems.models.punch;

// Represents a Punch model this is how it is also set up in the database

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.aqlabs.ems.models.timecard.TimeCard;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "punch")
public class Punch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonProperty("date")
    private Date date;

    @JsonProperty("timestamp")
    @Column(name = "time_stamp")
    private Time timestamp;

    @JsonProperty("status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_card_id")
    @JsonBackReference
    private TimeCard timeCard;
}
