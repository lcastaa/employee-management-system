package xyz.aqlabs.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.aqlabs.ems.models.timecard.TimeCard;

public interface TimeCardRepository extends JpaRepository<TimeCard,Integer> {



}
