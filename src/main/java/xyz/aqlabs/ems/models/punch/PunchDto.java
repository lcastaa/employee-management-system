package xyz.aqlabs.ems.models.punch;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PunchDto {

    @JsonProperty("employeeId")
    private Integer employeeId;

    @JsonProperty("timestamp")
    private Time timestamp;

    @JsonProperty("date")
    private Date date;

    @JsonProperty("status")
    private String status;

}
