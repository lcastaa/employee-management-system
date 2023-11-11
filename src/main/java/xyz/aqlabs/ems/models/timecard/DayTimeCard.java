package xyz.aqlabs.ems.models.timecard;

import lombok.*;
import xyz.aqlabs.ems.models.punch.Punch;
import xyz.aqlabs.ems.models.timecard.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "day_time_card")
public class DayTimeCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_of_punches")
    private Date dateOfPunches;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_card_id")
    private TimeCard timeCard;

    @OneToMany(mappedBy = "dayTimeCard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Punch> punches;

}
