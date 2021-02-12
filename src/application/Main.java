package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader; 
import javafx.stage.Stage;
import model.Course;
import model.CourseRegister;
import model.Result;
import model.Student;
import model.StudentRegister;
import model.WrittenExam;
import model.WrittenExamRegister;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application { 
	
	private static StudentRegister studentRegister = new StudentRegister();
	private static CourseRegister courseRegister = new CourseRegister();
	private static WrittenExamRegister writtenExamRegister = new WrittenExamRegister();
	
	//Methods
	public static StudentRegister getStudentRegister() {
		return studentRegister;
	}
	public static CourseRegister getCourseRegister() {
		return courseRegister;
	}
	public static WrittenExamRegister getWrittenExamRegister() {
		return writtenExamRegister;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		
		Parent root = FXMLLoader.load(getClass().getResource("/view/WelcomeView.fxml")); 
		Scene scene = new Scene(root);
		
		stage.setTitle("Contoso university");
		stage.setScene(scene); 
		stage.show();
	}

	public Main() {

		//Create students
		Student student1 = new Student("Theodor Detter");
		Student student2 = new Student("Isac Johansson");
		Student student3 = new Student("Fridmer Valenzuela");
		Student student4 = new Student("Peter Persson");
		Student student5 = new Student("Jonas Jonassson");
		
		//Adds students to studentRegister in Controller
		studentRegister.addStudent(student1);
		studentRegister.addStudent(student2);
		studentRegister.addStudent(student3);
		studentRegister.addStudent(student4);
		studentRegister.addStudent(student5); 
		
		//Create courses
		Course course1 = new Course("Unified Modeling Language", 3);
		Course course2 = new Course("Software development", 6);
		Course course3 = new Course("IS-project", 6);
		
		//Create CourseRegister
		
		courseRegister.addCourse(course1);
		courseRegister.addCourse(course2);
		courseRegister.addCourse(course3);
		
		//Create written exams
		WrittenExam exam1 = new WrittenExam("2021-02-01", "Room A123", "08:00", course1);
		WrittenExam exam2 = new WrittenExam("2021-02-02", "Room A167", "09:00", course2);
		WrittenExam exam3 = new WrittenExam("2021-02-03", "Room B198", "10:00", course3);
		
		course1.addWrittenExam(exam1);
		course2.addWrittenExam(exam2);
		course3.addWrittenExam(exam3);
		
		writtenExamRegister.addWrittenExam(exam1);
		writtenExamRegister.addWrittenExam(exam2);
		writtenExamRegister.addWrittenExam(exam3);

		//Create results
		Result result1 = new Result(85, student1, exam1);
		Result result2 = new Result(55, student1, exam2);
		Result result3 = new Result(49, student1, exam3);
		Result result4 = new Result(10, student2, exam3);
		
		exam1.addResult(result1);
		exam2.addResult(result2);
		exam3.addResult(result3);
		exam3.addResult(result4);
		
		student1.addResult(result1);
		student1.addResult(result2);
		student1.addResult(result3);
		student2.addResult(result4);
	}
	
	public static void main(String[] args) {
		launch(args); }	
}