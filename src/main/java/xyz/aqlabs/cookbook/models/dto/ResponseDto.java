package xyz.aqlabs.cookbook.models.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.aqlabs.cookbook.models.Employee;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private Employee employee;
    private String token;
}
