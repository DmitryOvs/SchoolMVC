package org.chukotka.school.server;

import org.chukotka.school.Model.Classes;
import org.chukotka.school.Model.Schedule;
import org.chukotka.school.Model.Schoolboy;
import org.chukotka.school.repository.ScheduleRepository;
import org.chukotka.school.repository.SchoolboyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SchoolboyServer {

    private final SchoolboyRepository schoolboyRepository;


    @Autowired
    public SchoolboyServer(SchoolboyRepository schoolboyRepository, ScheduleRepository scheduleRepository) {
        this.schoolboyRepository = schoolboyRepository;
    }

    public List<Schoolboy> findByKlass(Classes klass){
        return schoolboyRepository.findByKlass(klass);
    }

    public List<Schoolboy> findAll() {
        return schoolboyRepository.findAll();
    }

    public List<Schoolboy> findAndSortByName() {
        return schoolboyRepository.findAll(Sort.by("name"));
    }

    public List<Schoolboy> findAndPage(int page, int itemsOnPage) {
        return schoolboyRepository.findAll(PageRequest.of(page, itemsOnPage)).getContent();
    }

    public List<Schoolboy> findAndPageAndSortByName(int page, int itemsOnPage) {
        return schoolboyRepository.findAll(PageRequest.of(page, itemsOnPage, Sort.by("name"))).getContent();
    }

    public Schoolboy findById(int id) {
        Optional<Schoolboy> schoolboy = schoolboyRepository.findById(id);
        return schoolboy.orElse(null);
    }

    public List<Schoolboy> searchPupils(String startString) {
        return schoolboyRepository.findByNameStartingWithIgnoreCase(startString);
    }

    @Transactional
    public void save(Schoolboy schoolboy) {
        schoolboyRepository.save(schoolboy);
    }

    @Transactional
    public void update(int id, Schoolboy schoolboy) {
        Schoolboy schoolboyM = schoolboyRepository.findById(id).orElse(null);
        if (schoolboyM != null) {
            schoolboy.setKlass(schoolboy.getKlass());
        }
        schoolboy.setId(id);
        schoolboyRepository.save(schoolboy);
    }

    @Transactional
    public void addSchoolboyToClasses(Classes classes, int id) {
        Schoolboy schoolboy = schoolboyRepository.findById(id).orElse(null);
        if (schoolboy != null) {
            schoolboy.setKlass(classes);
            schoolboyRepository.save(schoolboy);
        }
    }

    @Transactional
    public void freeSchoolboy(int id) {
        Schoolboy schoolboy = schoolboyRepository.findById(id).orElse(null);
        if (schoolboy != null) {
            schoolboy.setKlass(null);
            schoolboyRepository.save(schoolboy);
        }
    }


    @Transactional
    public void deleteSchoolboy(int id) {
        Schoolboy deleteSchoolboy = schoolboyRepository.findById(id).orElse(null);
        if (deleteSchoolboy != null) {
            schoolboyRepository.delete(deleteSchoolboy);
        }
    }

    public Schoolboy findOneByName(String name) {
        return schoolboyRepository.findByName(name).orElse(null);
    }
}
