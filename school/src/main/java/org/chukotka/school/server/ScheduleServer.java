package org.chukotka.school.server;

import org.chukotka.school.Model.Classes;
import org.chukotka.school.Model.Schedule;
import org.chukotka.school.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ScheduleServer {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleServer(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> findByGlass(Classes glass){
        return scheduleRepository.findByGlass(glass);
    }

    public List<Schedule> findAll() {
        return scheduleRepository.findAll(Sort.by("name"));
    }

    public Schedule findById(int id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        return schedule.orElse(null);
    }

    @Transactional
    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Transactional
    public void update(int id, Schedule newSchedule) {
        Schedule schedule = scheduleRepository.findById(id).orElse(null);
        if (schedule != null) {
            newSchedule.setGlass(newSchedule.getGlass());
        }
        newSchedule.setId(id);
        scheduleRepository.save(newSchedule);
    }

    @Transactional
    public void deleteSchedule(int id) {
        Schedule deleteSchedule = scheduleRepository.findById(id).orElse(null);
        if (deleteSchedule != null) {
            scheduleRepository.delete(deleteSchedule);
        }
    }

    @Transactional
    public void addScheduleToClasses(Classes classes, int id) {
        Schedule schedule = scheduleRepository.findById(id).orElse(null);
        if (schedule != null) {
            schedule.setGlass(classes);
            scheduleRepository.save(schedule);
        }
    }

    @Transactional
    public void freeSchedule(int id) {
        Schedule schedule = scheduleRepository.findById(id).orElse(null);
        if (schedule != null) {
            schedule.setGlass(null);
            scheduleRepository.save(schedule);
        }
    }

}
