package lv.venta.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lv.venta.model.enums.Degree;
import lv.venta.model.Course;
import lv.venta.model.Grade;
import lv.venta.model.Professor;
import lv.venta.service.IFilterService;

@Controller
@RequestMapping("/filter")
public class FilterController {
	
	@Autowired
	private IFilterService filterService;
	
	// localhost:9000/filter/professor/degree/phd
	@GetMapping("/professor/degree/{inputdegree}")
	public String getProfessorsByDegree(@PathVariable(name = "inputdegree") Degree inputdegree, Model model) {
		try
		{
			ArrayList<Professor>professorsFromDB = filterService.filterProfessorsByDegree(inputdegree);
			model.addAttribute("package", professorsFromDB);
			return "show-multiple-professors-page";
		}
		catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "error-page";
		}
	}
	
	@GetMapping("/course/professor/{id}")
	public String getCourseByProfessorId(@PathVariable(name = "id") long id, Model model) {
		try
		{
			ArrayList<Course> coursesFromDB = filterService.filterCoursesByPorfessorId(id);
			model.addAttribute("package", coursesFromDB);
			return "show-multiple-courses-page";
		}
		catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "error-page";
		}
		
	}
	
	@GetMapping("/grade/student/{name}/{surname}")
	public String getGradesByStudentNameAndSurname(@PathVariable(name = "name") String name,
			@PathVariable(name = "surname") String surname, Model model) {
		try
		{
			ArrayList<Grade> gradesFromDB = filterService.filterGradesByStudentNameAndSurname(name, surname);
			model.addAttribute("package", gradesFromDB);
			return "show-multiple-grades-page";
		}
		catch (Exception e) {
			model.addAttribute("package", e.getMessage());
			return "error-page";
		}
		
	}
	
}
