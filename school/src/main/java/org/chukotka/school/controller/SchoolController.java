package org.chukotka.school.controller;

import org.chukotka.school.Model.Schoolboy;
import org.chukotka.school.server.SchoolboyServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class SchoolController {

    @GetMapping("/school")
    public String showSchool(){
        return "school";
    }

}





