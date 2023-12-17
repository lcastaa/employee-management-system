package xyz.aqlabs.ems.models.employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.aqlabs.ems.models.authentication.Role;
import xyz.aqlabs.ems.models.authentication.ShiftCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "employee")
@Entity
public class Employee implements UserDetails {

    @Id
    @GeneratedValue
    @JsonProperty("employeeId")
    private Integer employeeId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("dateOfBirth")
    private LocalDate dateOfBirth;

    @JsonProperty("payRate")
    private Double payRate;

    @JsonIgnore
    private String password;

    @JsonProperty("role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonProperty("shiftCode")
    @Enumerated(EnumType.STRING)
    private ShiftCode shiftCode;

    @Override @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}
