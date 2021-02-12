package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CourseRegister {
	private ObservableList<Course> courseRegister = FXCollections.observableArrayList();

	//Getters
	public ObservableList<Course> getCourseRegister() {
		return courseRegister;
	}

	//Methods
	public void addCourse(Course c) {
		boolean existingCourse = false;

		for (Course courseTmp: courseRegister) {

			//Checks if course code is unique or not
			if (courseTmp.getCourseCode().equals(c.getCourseCode())) {	
				existingCourse = true;
			}
		}
		//If course code is unique it will be addded to the register
		if (existingCourse == false) {										
			courseRegister.add(c);	
		}
	}

	public Course removeCourse(String courseCode) { 

		for (Course courseTmp : courseRegister) {
			if (courseTmp.getCourseCode().equals(courseCode)) {

				courseRegister.remove(courseTmp);
				return courseTmp;
			}
		}
		return null;
	}

	public Course findCourse(String courseCode) {
		for (Course c : courseRegister) {
			if (c.getCourseCode().equals(courseCode)) {
				return c;
			}
		}
		return null;
	}
}
