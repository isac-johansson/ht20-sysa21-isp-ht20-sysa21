package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentRegister {
	private static ObservableList<Student> studentRegister = FXCollections.observableArrayList();
	
	//Getters
	public ObservableList<Student> getStudentRegister() {
		return studentRegister;
	}

	//Methods
	public void addStudent(Student student) {
		boolean existingStudent = false;

		for (Student studentTmp: studentRegister) {

			//Checks if studentID is unique or not. Not acutally needed since the ID is autogenerated, but just in case.
			if (studentTmp.getStudentID().equals(student.getStudentID())) {	
				existingStudent = true;
			}
		}
		if (existingStudent == false) {										
			studentRegister.add(student);	
		}
	}

	public Student removeStudent(String studentID) { 

		for (Student student : studentRegister) {
			if (student.getStudentID().equals(studentID)) {

				studentRegister.remove(student);
				return student;
			}
		}
		return null;
	}

	public Student findStudent(String studentID) {
		for (Student student : studentRegister) {
			if (student.getStudentID().equals(studentID)) {
				return student;
			}
		}
		return null;
	}
}