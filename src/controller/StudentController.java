
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import application.Main;
import javafx.event.ActionEvent; 
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Student;
import model.StudentRegister;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;

public class StudentController implements Initializable {

	@FXML
	private TableView<Student> tableStudent;
	@FXML
	private TableColumn<Student, String> columnName;
	@FXML
	private TableColumn<Student, String> columnStudentID;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtStudentID;

	//Connects registers in Main

	private StudentRegister studentRegister = Main.getStudentRegister();

	//Menu buttons

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private void loadWelcomeView(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/WelcomeView.fxml")); 

		anchorPane.getChildren().setAll(pane);
	}

	//Error message method
	private void addErrorMessage(String errorMessage) {
		
	Alert alert = new Alert(AlertType.ERROR);
	alert.setTitle("Error");
	alert.setHeaderText("Something went wrong");
	alert.setContentText(errorMessage);
	alert.showAndWait();
	}
	
	//Student button actions
	
	//Add Student button
	@FXML
	public void addStudent(ActionEvent event) {
		String name = txtName.getText();

		if (name.trim().isEmpty()) {

			addErrorMessage("Please type in a name to add a new student.");
		
		//System will only generate IDs between 10000-99999
		} else if (Student.getStartNbr() > Student.getMaxNbr()) { 

			addErrorMessage("Sorry but the system has reached its maximum number of unique identifiers assigned. The student has not been added to the system.");

		} else {

			Student student = new Student(name);

			studentRegister.addStudent(student);

			//Clear TextArea
			txtName.clear();  
		}	
	}
	//Update Student button
	@FXML
	public void updateStudent(ActionEvent event) {
		String studentID = txtStudentID.getText();
		String newName = txtName.getText();

		if (studentID.trim().isEmpty()) {

			addErrorMessage("Please type in a student ID to update a student.");

		} else if (studentRegister.findStudent(studentID) == null) {

			addErrorMessage("The student ID " + studentID + " does not exist. Please try again!");
			
			//Clear TextArea
			txtStudentID.clear();

		} else if (newName.trim().isEmpty()) { 

			addErrorMessage("Please type in a new name to update the student.");

		} else if (!studentID.trim().isEmpty()){

			Student student = studentRegister.findStudent(studentID);
			student.setName(newName);

			//Refresh table
			tableStudent.refresh();

			//Clear TextArea
			txtName.clear();  
			txtStudentID.clear();  
		}
	}
	//Remove Student Button
	@FXML
	public void removeStudent(ActionEvent event) {

		String studentID = txtStudentID.getText();

		//Checks if studentID exists. txtStudentID.getText() can never be null, it will be contain "" in no input is made. Therefore .trim() is needed.
		if (studentID.trim().isEmpty()) {

			addErrorMessage("Please type in a student ID to delete a student.");

		} else if (studentRegister.findStudent(studentID) == null) {

			addErrorMessage("The student ID " + studentID + " does not exist. Please try again!");
			txtStudentID.clear();

		} else {

			//Alert pop up window
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Please confirm");
			alert.setHeaderText("Are you sure you want to remove student " + studentID + "?");

			ButtonType buttonTypeYes = new ButtonType("Yes");
			ButtonType buttonTypeNo = new ButtonType("No", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == buttonTypeYes){

				//Remove Student from studentRegister
				studentRegister.removeStudent(studentID);

				//Clear TextArea
				txtStudentID.clear();
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resourceBundle) {

		//TableViews

		//columnName = Name of the column you want to populate. ("name") = Name of the variable in the corresponding class. In this case class Student as studentRegister contains Students.
		columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnStudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
		//tableStudent = Name of the table you want to populate. (studentRegister.getObservableList()) = reference to the ObservableList you want to populate the table with.
		tableStudent.setItems(studentRegister.getStudentRegister());
	}
}