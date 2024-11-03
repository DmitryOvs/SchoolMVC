package org.chukotka.school.repository;

import org.chukotka.school.Model.Classes;
import org.chukotka.school.Model.Schoolboy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface SchoolboyRepository extends JpaRepository<Schoolboy, Integer> {

    List<Schoolboy> findByKlass(Classes klass);

    Optional<Schoolboy> findByName(String name);

    List<Schoolboy> findByNameStartingWithIgnoreCase(String startString);
}



