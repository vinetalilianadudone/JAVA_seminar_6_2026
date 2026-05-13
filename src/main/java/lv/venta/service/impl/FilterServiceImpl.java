package lv.venta.service.impl;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lv.venta.model.Course;
import lv.venta.model.Grade;
import lv.venta.model.Professor;
import lv.venta.model.Student;
import lv.venta.model.enums.Degree;
import lv.venta.repo.ICourseRepo;
import lv.venta.repo.IGradeRepo; 
import lv.venta.repo.IProfessorRepo;
import lv.venta.repo.IStudentRepo; 
import lv.venta.service.IFilterService;

@Service
public class FilterServiceImpl implements IFilterService {
	
    @Autowired
    private IProfessorRepo profRepo;
    
    @Autowired
    private ICourseRepo courseRepo;

    @Autowired
    private IGradeRepo gradeRepo;

    @Autowired
    private IStudentRepo studentRepo;
	
    @Override
    public ArrayList<Professor> filterProfessorByDegree(Degree degree) throws Exception {
        if (degree == null) {
            throw new Exception("Grāds nevar būt null");
        }
        ArrayList<Professor> result = profRepo.findByDegree(degree);
        if (result.isEmpty()) {
            throw new Exception("Nav profesoru ar grādu: " + degree);
        }
        return result;
    }
	
    @Override
    public ArrayList<Course> filterCoursesByPorfessorId(long id) throws Exception {
        if (id <= 0) {
            throw new Exception("ID jābūt pozitīvam");
        }
        if (!profRepo.existsById(id)) {
            throw new Exception("Nevar atgriezt kursus, jo profesors ar id " + id + " neeksistē");
        }
        ArrayList<Course> result = courseRepo.findByProfessorPid(id);
        
        if (result.isEmpty()) {
            throw new Exception("Professoram ar id " + id + " nav piesaistīts neviens kurss");
        }
        return result;
    }
	
    @Override
    public ArrayList<Grade> filterGradesByStudentNameAndSurname(String name, String surname) throws Exception {
        if (name == null || surname == null) {
            throw new Exception("Vārds vai uzvārds nevar būt tukšs");
        }
        if (!studentRepo.existsByNameAndSurname(name, surname)) {
        	throw new Exception("Students " + name + " " + surname + " neeksistē");
        }
        ArrayList<Grade> result = gradeRepo.findByStudentNameAndStudentSurname(name, surname);
        if (result.isEmpty()) {
            throw new Exception("Studentam " + name + " " + surname + " nav nevienas atzīmes");
        }
        return result;
    }
	
    @Override
    public float calculateAvgGradeByCourseTitle(String title) throws Exception {
        if (title == null || title.isEmpty()) {
            throw new Exception("Kursa nosaukums nevar būt tukšs");
        }
        if (!courseRepo.existsByTitle(title)) {
            throw new Exception("Kurss ar nosaukumu " + title + " neeksistē");
        }
        
        float result = gradeRepo.calculateAVGGradeForCourse(title);
        
        if (result == 0) {
            throw new Exception("Kursā " + title + " nav nevienas atzīmes, lai aprēķinātu vidējo");
        }
        return result;
    }


    @Override
    public ArrayList<Student> filterStudentsWithFailedGrades() throws Exception {
    	if (studentRepo.count() == 0) {
    		throw new Exception("Studentu tabula ir tukša");
    	}
    	if (gradeRepo.count() == 0) {
    		throw new Exception("Atzimju tabula ir tukša");
    	}
    	ArrayList<Student> result = studentRepo.findByGradesGrvalueLessThan(4);
        if (result.isEmpty()) {
            throw new Exception("Nav neviena studenta ar nesekmīgām atzīmēm");
        }
        return result;
    }

	@Override
	public ArrayList<Professor> filterProfessorsByDegree(Degree inputdegree) {
		// TODO Auto-generated method stub
		return null;
	}
}