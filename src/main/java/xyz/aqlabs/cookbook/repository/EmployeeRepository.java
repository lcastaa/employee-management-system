package xyz.aqlabs.cookbook.repository;

/*
This Class contains the implementations, anything that will interact with the
employee database will need to have.
*/

import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.aqlabs.cookbook.models.Employee;

import java.util.Optional;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


    // --------|Update, Delete Operations |--------
    @Modifying @Transactional @Query("UPDATE Employee employee SET employee.username = :username, employee.email = :email WHERE employee.employeeId = :employeeId")
    void updateEmployee(@Param(value = "employeeId") Integer employeeId,
                        @Param(value = "username") String username,
                        @Param(value = "email") String email
    );


    @Modifying @Transactional @Query("DELETE FROM Employee employee WHERE employee.employeeId = :employeeId")
    void deleteEmployee(
            @Param(value = "employeeId") Integer employeeId
    );


    //---------| Query Methods [Default] |--------

    Employee findByEmployeeId(Integer employeeId);


    Optional<Employee> findByEmail(String email);

    @NonNull
    Optional<Employee> findByUsername(String username);

}

