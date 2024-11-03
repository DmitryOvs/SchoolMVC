package org.chukotka.school.controller;


import jakarta.validation.Valid;
import org.chukotka.school.Model.Classes;
import org.chukotka.school.Model.Schoolboy;
import org.chukotka.school.server.ClassesServer;
import org.chukotka.school.server.ScheduleServer;
import org.chukotka.school.server.SchoolboyServer;
import org.chukotka.school.validator.UniqueSchoolboyValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Controller
@RequestMapping("/school/schoolboy")
public class PupilsController {

    private final SchoolboyServer schoolboyServer;
    private final ClassesServer classesServer;
    private final ScheduleServer scheduleServer;
    private final UniqueSchoolboyValidator  validator;

@Autowired
    public PupilsController(SchoolboyServer schoolboyServer, ClassesServer classesServer, ScheduleServer scheduleServer, UniqueSchoolboyValidator validator) {
        this.schoolboyServer = schoolboyServer;
        this.classesServer = classesServer;
        this.scheduleServer = scheduleServer;
        this.validator = validator;
}
    @GetMapping
    public String showBooks(@RequestParam(value = "page", required = false, defaultValue = "-1") int page,
                            @RequestParam(value = "schoolboyOnPage", required = false, defaultValue = "-1") int schoolboyOnPage,
                            @RequestParam(value = "sortByName", required = false, defaultValue = "false") boolean sortByName,
                            Model model) {
        List<Schoolboy> pupils;
        if (page != -1 && schoolboyOnPage != -1) {
            pupils = sortByName
                    ? schoolboyServer.findAndPageAndSortByName(page, schoolboyOnPage)
                    : schoolboyServer.findAndPage(page, schoolboyOnPage);
        } else if (page == -1 && schoolboyOnPage == -1 && sortByName) {
            pupils = schoolboyServer.findAndSortByName();
        } else {
            pupils = schoolboyServer.findAll();
        }
        model.addAttribute("pupils", pupils);
        return "schoolboy/show";
    }

    @GetMapping("/{id}")
    public String showSchoolboy(@PathVariable int id, Model model,
                                @ModelAttribute("klass") Classes klass) {
    Schoolboy schoolboy = schoolboyServer.findById(id);
    model.addAttribute("schoolboy", schoolboy);
    if (schoolboy.getKlass() != null){
        model.addAttribute("klass", schoolboy.getKlass());
    }else {
        model.addAttribute("classes", classesServer.findAll());
    }
    return "schoolboy/profile";
    }

    @GetMapping("/search")
    public String searchBook(@RequestParam(required = false, defaultValue = "") String startString,
                             Model model) {
        model.addAttribute("startString", startString);
        if (!startString.isEmpty()) {
            model.addAttribute("pupils", schoolboyServer.searchPupils(startString));
        }
        return "schoolboy/search";
    }

    @GetMapping("/new")
    public String addSchoolboy(@ModelAttribute("schoolboy") Schoolboy schoolboy){
        return "schoolboy/new";
    }


    @PostMapping()
    public String creatNewSchoolboy(@ModelAttribute("schoolboy") @Valid Schoolboy schoolboy,
                                    BindingResult bindingResult) {
    validator.validate(schoolboy, bindingResult);
    if (bindingResult.hasErrors())
    {return "schoolboy/new";}
    schoolboyServer.save(schoolboy);
    return "redirect:/school/schoolboy";
    }

    @GetMapping("/{id}/edit")
    public String editSchoolboy(@PathVariable int id, Model model) {
    model.addAttribute("schoolboy", schoolboyServer.findById(id));
    return "schoolboy/edit";
    }


    @PatchMapping("/{id}")
    public String updateSchoolboy(@ModelAttribute("schoolboy") @Valid Schoolboy schoolboy,
                                  BindingResult bindingResult, @PathVariable("id") int id) {
    validator.validate(schoolboy, bindingResult);
    if (bindingResult.hasErrors()) return "schoolboy/edit";
    schoolboyServer.update(id, schoolboy);
    return "redirect:/school/schoolboy";
    }

    @PatchMapping("/{id}/free")
    public String freeSchoolboy(@PathVariable("id") int id) {
    schoolboyServer.freeSchoolboy(id);
    return "redirect:/school/schoolboy/{id}";
    }

    @PatchMapping("/{id}/classes")
    public String addSchoolboyToClasses(@ModelAttribute Classes classes, @PathVariable int id) {
        schoolboyServer.addSchoolboyToClasses(classes, id);
        scheduleServer.addScheduleToClasses(classes, id);
        return "redirect:/school/schoolboy/{id}";
    }

    @DeleteMapping("/{id}")
    public String deleteSchoolboy(@PathVariable("id") int id) {
    schoolboyServer.deleteSchoolboy(id);
    return "redirect:/school/schoolboy";
    }

}
