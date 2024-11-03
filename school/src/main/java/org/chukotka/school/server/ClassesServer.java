package org.chukotka.school.server;

import org.chukotka.school.Model.Classes;
import org.chukotka.school.repository.ClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClassesServer {

private final ClassesRepository classesRepository;

@Autowired
    public ClassesServer(ClassesRepository classesRepository) {
        this.classesRepository = classesRepository;
    }
    public List<Classes> findAll() {
    return classesRepository.findAll(Sort.by("name"));
    }

    public Classes findById(int id) {
        Optional<Classes> classes = classesRepository.findById(id);
        return classes.orElse(null);
    }

    @Transactional
    public void save(Classes clas) {
        classesRepository.save(clas);
    }

    @Transactional
    public void update(int id, Classes clas) {
        clas.setId(id);
        classesRepository.save(clas);
    }

    @Transactional
    public void delete(int id){
        classesRepository.deleteById(id);
    }

}
