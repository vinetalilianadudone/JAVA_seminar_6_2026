package lv.venta.service;

import java.util.ArrayList;

import lv.venta.model.Student;

public interface IStudentCRUDService {
		
		// C - create
		public abstract void create(String name, String surname)
				throws Exception;
		
		// R - retrieve all
		public abstract ArrayList<Student> retrieveAll() 
	    		throws Exception;
		
		// R - retrieve by Sid
		public abstract Student retrieveBySid(long sid)
				throws Exception;

	    // U - update
		public abstract void updateBySid(long sid, String name, String surname) 
	    		throws Exception;

	    // D - delete
		public abstract void deleteBySid(long sid)
				throws Exception;
		
	}