package xyz.aqlabs.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.aqlabs.ems.models.punch.Punch;


@Repository
public interface PunchRepository extends JpaRepository<Punch,Integer> {



}
