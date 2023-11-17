package xyz.aqlabs.ems.models.timecard;

// This is a TimeCardDto used by the front-end to pass models back and forth from the back-end

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimeCardDto {

    @JsonProperty("employeeId")
    private Integer employeeId;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("endDate")
    private String endDate;

}
