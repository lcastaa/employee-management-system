package xyz.aqlabs.ems.models.authentication;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.aqlabs.ems.models.employee.Employee;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private Employee employee;
    private String token;
}
