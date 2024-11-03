package org.chukotka.school.controller;


import jakarta.validation.Valid;
import org.chukotka.school.Model.Classes;
import org.chukotka.school.Model.Schedule;
import org.chukotka.school.server.ClassesServer;
import org.chukotka.school.server.ScheduleServer;
import org.chukotka.school.server.SchoolboyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("school/schedule")
public class ScheduleController {

    private final ScheduleServer scheduleServer;
    private final ClassesServer classesServer;
    private final SchoolboyServer schoolboyServer;

    @Autowired
    public ScheduleController(ScheduleServer scheduleServer, ClassesServer classesServer, SchoolboyServer schoolboyServer) {
        this.scheduleServer = scheduleServer;
        this.classesServer = classesServer;
        this.schoolboyServer = schoolboyServer;
    }

    @GetMapping()
    public String showSchedule(Model model) {
        model.addAttribute("scheduleS", scheduleServer.findAll());
        return "schedule/show";
    }

    @GetMapping("/{id}")
    public String showSchedule(@PathVariable int id, Model model,
                                @ModelAttribute("glass") Classes glass) {
        Schedule schedule = scheduleServer.findById(id);
        model.addAttribute("schedule", schedule);
        if (schedule.getGlass() != null){
            model.addAttribute("glass", schedule.getGlass());
        }else {
            model.addAttribute("classe", classesServer.findAll());
        }
        return "schedule/profile";
    }

    @GetMapping("/new")
    public String addSchedule(@ModelAttribute("schedule") Schedule schedule){
        return "schedule/new";
    }

    @PostMapping()
    public String greateSchedule(@ModelAttribute("schedule") @Valid Schedule schedule,
                                 BindingResult bindingResult){
        if (bindingResult.hasErrors()) {return "schedule/new"; }
        scheduleServer.save(schedule);
        return "redirect:/school/schedule";
    }

    @GetMapping("/{id}/edit")
    public String editSchedule(@PathVariable int id, Model model){
        model.addAttribute("schedule", scheduleServer.findById(id));
        return "schedule/edit";
    }

    @PatchMapping("/{id}")
    public String updateSchedule(@ModelAttribute("schedule") @Valid Schedule schedule,
                                 BindingResult bindingResult, @PathVariable("id") int id){
        if (bindingResult.hasErrors()) {
            return "schedule/edit";
        }
        scheduleServer.update(id, schedule);
        return "redirect:/school/schedule";
    }

    @PatchMapping("/{id}/free")
    public String freeSchedule(@PathVariable("id") int id) {
        scheduleServer.freeSchedule(id);
        return "redirect:/school/schedule/{id}";
    }

    @PatchMapping("/{id}/classes")
    public String addScheduleToClasses(@ModelAttribute("classes") Classes classes, @PathVariable("id") int id) {
        scheduleServer.addScheduleToClasses(classes, id);
        schoolboyServer.addSchoolboyToClasses(classes, id);
        return "redirect:/school/schedule/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteSchedule(@PathVariable("id") int id){
        scheduleServer.deleteSchedule(id);
        return "redirect:/school/schedule";
    }
}
