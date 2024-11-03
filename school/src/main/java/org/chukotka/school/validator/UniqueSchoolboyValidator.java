package org.chukotka.school.validator;

import org.chukotka.school.Model.Schoolboy;
import org.chukotka.school.server.SchoolboyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UniqueSchoolboyValidator implements Validator {
    private final SchoolboyServer schoolboyServer;

    @Autowired
    public UniqueSchoolboyValidator(SchoolboyServer schoolboyServer) {
        this.schoolboyServer = schoolboyServer;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return Schoolboy.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Schoolboy schoolboy = (Schoolboy) target;
        Schoolboy schoolboyName = schoolboyServer.findOneByName(schoolboy.getName());
        if (schoolboyName != null && schoolboy.getId() != schoolboyName.getId()) {
            errors.rejectValue("name", "schoolboyName", "Duplicate schoolboy");
        }
    }
}