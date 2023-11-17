package xyz.aqlabs.ems.models.punch;

// This is a PunchDto used by the front-end to pass models back and forth from the back-end

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PunchDto {

    @JsonProperty("timeCardId")
    private Integer timeCardId;

    @JsonProperty("date")
    private String date;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("status")
    private String status;

}
