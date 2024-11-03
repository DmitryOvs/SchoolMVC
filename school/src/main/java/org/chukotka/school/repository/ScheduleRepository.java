package org.chukotka.school.repository;

import org.chukotka.school.Model.Classes;
import org.chukotka.school.Model.Schedule;
import org.chukotka.school.Model.Schoolboy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    List<Schedule> findByGlass(Classes glass);
}
