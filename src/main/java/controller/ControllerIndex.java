package controller;

import org.apache.tiles.autotag.core.runtime.annotation.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.Student;
import service.StudentServiceimpl;

import java.util.List;

@Controller
public class ControllerIndex {
    @Autowired
    StudentServiceimpl userService;
    @RequestMapping(value = "/index")
    public String index(Model model){
     List<Student> student = userService.allStudent();
        model.addAttribute(student);
        return "index";
    }

    @RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
    public String addStudent(Model model,@PathVariable int id){
        model.addAttribute(userService.queryUser(id));
        return "addStudent";
    }
    @RequestMapping(value = "/stu", method = RequestMethod.GET)
    public String forwordStudent(){
        return "editStudent";
    }
    @RequestMapping(value = "/stu", method = RequestMethod.POST)
    public String addStudent(Student student){
        userService.addUser(student);
        return "redirect:/index";
    }

    @RequestMapping(value = "/stu/{id}", method = RequestMethod.DELETE)
    public String deleteStu(@PathVariable int id){
        userService.deleteUser(id);
        return "redirect:/index";
    }

}
