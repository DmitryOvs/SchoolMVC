package org.chukotka.school.controller;


import jakarta.validation.Valid;
import org.chukotka.school.Model.Classes;
import org.chukotka.school.Model.Schedule;
import org.chukotka.school.Model.Schoolboy;
import org.chukotka.school.server.ClassesServer;
import org.chukotka.school.server.ScheduleServer;
import org.chukotka.school.server.SchoolboyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/school/classes")
public class ClassesController {

private final ClassesServer classesServer;
private final SchoolboyServer schoolboyServer;
private final ScheduleServer scheduleServer;

@Autowired
    public ClassesController(ClassesServer classesServer, SchoolboyServer schoolboyServer, ScheduleServer scheduleServer) {
        this.classesServer = classesServer;
        this.schoolboyServer = schoolboyServer;
        this.scheduleServer = scheduleServer;
}

    @GetMapping()
    public String showClasses(Model model) {
    model.addAttribute("classes", classesServer.findAll());
    return "classes/show";
    }

    @GetMapping("/{id}")
    public String showClass(@PathVariable int id, Model model) {
        Classes clas = classesServer.findById(id);
        List<Schoolboy> pupils = schoolboyServer.findByKlass(clas);
        List<Schedule> schedules = scheduleServer.findByGlass(clas);
        model.addAttribute("clas", clas);

        if (!pupils.isEmpty() && !schedules.isEmpty()){
            model.addAttribute("pupils", pupils);
            model.addAttribute("schedules", schedules);
        } else if (!pupils.isEmpty()) {
            model.addAttribute("pupils", pupils);
        }else if (!schedules.isEmpty()) {
            model.addAttribute("schedules", schedules);
        }
        return "classes/profile";
    }

    @GetMapping("/new")
    public String showNew(@ModelAttribute("clas") Classes clas) {
    return "classes/new";
    }

    @PostMapping()
    public String saveClass(@ModelAttribute("clas") @Valid Classes clas,
                            BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        return "classes/new";
    }
    classesServer.save(clas);
    return "redirect:/school/classes";
    }

    @GetMapping("/{id}/edit")
    public String showEdit(@PathVariable int id, Model model) {
    model.addAttribute("clas", classesServer.findById(id));
    return "classes/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("clas") @Valid Classes clas,
                         BindingResult bindingResult, @PathVariable("id") int id) {
    if (bindingResult.hasErrors()) {return "classes/edit";}
    classesServer.update(id, clas);
    return "redirect:/school/classes";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
    classesServer.delete(id);
    return "redirect:/school/classes";
    }
}
