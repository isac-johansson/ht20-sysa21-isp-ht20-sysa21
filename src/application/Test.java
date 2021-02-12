
package application;

import model.Course;
import model.CourseRegister;
import model.Result;
import model.Student;
import model.StudentRegister;
import model.WrittenExam;
import model.WrittenExamRegister;

public class Test {

	public static void main(String[] args) {
	
		//Create students
		Student student1 = new Student("S10001", "Theodor Detter");
		Student student2 = new Student("S10002", "Isac Johansson");
		Student student3 = new Student("S10003", "Fridmer Valenzuela");
		
		System.out.println("System created three new students: ");
		System.out.println(student1.getName() + ", " + student2.getName() + " and " + student3.getName());
		
		//Create StudentRegister
		StudentRegister studentRegister = new StudentRegister();
		
		studentRegister.addStudent(student1);
		studentRegister.addStudent(student2);
		studentRegister.addStudent(student3);
		
		System.out.println(student1.getName() + ", " + student2.getName() + " and " + student3.getName() + " added to StudentRegister.");
		
		
		//Create courses
		Course course1 = new Course("C10001", "Unified Modeling Language", 3);
		Course course2 = new Course("C10002", "Software development", 6);
		Course course3 = new Course("C10003", "IS-project", 6);
		Course course4 = new Course("C10004", "Introdcution to Information Systems", 6);
	
		System.out.println('\n' + "System added four new Courses: ");
		System.out.println( course1.getName() + ", " + course2.getName() + ", " + course3.getName() + " and " + course4.getName());
		
		//Create CourseRegister
		CourseRegister courseRegister = new CourseRegister();
		
		courseRegister.addCourse(course1);
		courseRegister.addCourse(course2);
		courseRegister.addCourse(course3);
		courseRegister.addCourse(course4);
		
		System.out.println(course1.getName() + ", " + course2.getName() + ", " + course3.getName() + " and " + course4.getName() + " added to CourseRegister.");
		
		//Create written exams
		WrittenExam exam1 = new WrittenExam("E00001", "2021-02-01", "Room A123", "08:15", course1);	
		WrittenExam exam2 = new WrittenExam("E00002", "2021-02-02", "Room A167", "09:15", course2);		
		WrittenExam exam3 = new WrittenExam("E00003", "2021-02-03", "Room B198", "10:15", course3);
		WrittenExam exam4 = new WrittenExam("E00004", "2021-02-04", "Room B067", "11:15", course4);	

		course1.addWrittenExam(exam1);
		course2.addWrittenExam(exam2);
		course3.addWrittenExam(exam3);
		course4.addWrittenExam(exam4);
		
		System.out.println('\n' + "System added four new Exams: ");
		System.out.println( exam1 + ", " + exam2 + ", " + exam3 + " and " + exam4);
		
		//Create WrittenExamRegister
		WrittenExamRegister writtenExamRegister = new WrittenExamRegister();
		
		writtenExamRegister.addWrittenExam(exam1);
		writtenExamRegister.addWrittenExam(exam2);
		writtenExamRegister.addWrittenExam(exam3);
		writtenExamRegister.addWrittenExam(exam4);
		
		System.out.println(exam1.getExamID() + ", " + exam2.getExamID() + ", " + exam3.getExamID() + " and " + exam4.getExamID() + " added to CourseRegister.");


		//Create results
		Result result1 = new Result(85, student1, exam1);
		Result result2 = new Result(55, student1, exam2);
		Result result3 = new Result(49, student1, exam3);
		
		exam1.addResult(result1);
		exam2.addResult(result2);
		exam2.addResult(result3);	
		
		System.out.println('\n' + "System added four new results for " + student1.getName() + ": ");
		System.out.println(result1.getLetterGrade() + " for " + course1.getName() + ", " + result2.getLetterGrade() + " for " + course2.getName() + " and " + result3.getLetterGrade() + " for " + course3.getName() + ".");
		
		//Find and update student name
		System.out.println("\n" + "Find StudentID for Student 3: " + studentRegister.findStudent(student3.getStudentID()));
		System.out.println("Update name for " + student3.getStudentID() + " to John Johansson" );
		student3.setName("John Johansson");
		System.out.println("Name changed to " + student3.getName());
		
		//Remove student
		System.out.println("Remove Student 3 from StudentRegister");
		studentRegister.removeStudent(student3.getStudentID());
		System.out.println("Removed Student 3");

		//Find and update course name
		System.out.println("\n" + "Find CourseCode for Course 1: " + courseRegister.findCourse(course1.getCourseCode()));
		System.out.println("Update name for course in " + course1.getName() + " to UML" );
		course1.setName("UML");
		System.out.println("Name changed to " + course1.getName());
		
		//Remove course
		System.out.println("Remove " + course1.getName() + " from CourseRegister");
		courseRegister.removeCourse(course1.getCourseCode());
		System.out.println("Removed " + course1.getName());
		
		//Remove Written Exam from course
		System.out.println("\n" + "Find and remove Written Exam 1 from Course 1");
		System.out.println("Written Exam 1 examID is: " + course1.getWrittenExam() + "\n" + "Remove " + course1.getWrittenExam());
		writtenExamRegister.removeWrittenExam(exam1.getExamID());
		course1.removeWrittenExam(exam1.getExamID());
		System.out.println("Removed Written Exam 1 from Course 1");
	}

}