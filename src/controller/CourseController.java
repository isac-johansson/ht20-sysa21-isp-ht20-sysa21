
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Course;
import model.CourseRegister;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;

public class CourseController implements Initializable {

	@FXML
	private TableView<Course> tableCourse;
	@FXML
	private TableColumn<Course, String> columnCourseCode;
	@FXML
	private TableColumn<Course, String> columnCourseName;
	@FXML
	private TableColumn<Course, Integer> columnCourseCredits;
	@FXML
	private TextField txtCourseName;
	@FXML
	private TextField txtCourseCode;
	@FXML
	private ChoiceBox<Integer> choiceBoxCredits;

	//Connects registers in Main
	private CourseRegister courseRegister = Main.getCourseRegister();

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

	//Course button actions
	
	//Add Course button
	@FXML
	public void addCourse(ActionEvent event) {

		String courseName = txtCourseName.getText();
		Integer creditsSelected = choiceBoxCredits.getValue();

		if (courseName.trim().isEmpty() && creditsSelected == null) { 

			addErrorMessage("Please type in a course name and select the amount of credits.");

		} else {

			if (courseName.trim().isEmpty()) { 

				addErrorMessage("Please type in a course name to create a new course.");


			} else if (creditsSelected == null) { 

				addErrorMessage("Please select the amount of credits");

			//System will only generate IDs between 10000-99999
			} else if (Course.getStartNbr() > Course.getMaxNbr()) { 

				addErrorMessage("Sorry but the system has reached its maximum number of unique identifiers assigned. The course has not been added to the system.");

			} else {

				Course course = new Course(courseName, creditsSelected);
				courseRegister.addCourse(course);

				//Clear TextArea
				txtCourseName.clear();  
				txtCourseCode.clear();  

				//Clear ChoiceBoxes
				choiceBoxCredits.setValue(null);
			}
		}
	}
	//Update Course button
	@FXML
	public void updateCourse(ActionEvent event) {

		String courseName = txtCourseName.getText();
		Integer creditsSelected = choiceBoxCredits.getValue();
		String courseCode = txtCourseCode.getText();		

		if (courseCode.trim().isEmpty()) {

			addErrorMessage("Please type in a course code to update a course.");

		} else if (courseRegister.findCourse(courseCode) == null) {

			addErrorMessage("The course code " + courseCode + " does not exist. Please try again!");
			
			//Clear TextArea
			txtCourseCode.clear();

		} else if (courseName.trim().isEmpty() && creditsSelected == null) { 

			addErrorMessage("Please type in an new name or credits to update the course.");

		} else {

			Course course = courseRegister.findCourse(courseCode);

			//If statements to accomodate for updating only the selections made
			if (!courseName.trim().isEmpty()) {
				course.setName(courseName);
			}
			if (creditsSelected != null) {
				course.setCredits(creditsSelected);
			}

			//Refresh table
			tableCourse.refresh();

			//Clear TextArea
			txtCourseName.clear();  
			choiceBoxCredits.setValue(null);
			txtCourseCode.clear();  
		}
	}
	//Remove Course Buttons
	@FXML
	public void removeCourse(ActionEvent event) {

		String courseCode = txtCourseCode.getText();

		//Checks if examID exists. txtExamID.getText() can never be null, it will be contain "" in no input is made. Therefore .trim() is needed.
		if (courseCode.trim().isEmpty()) {

			addErrorMessage("Please type in a course code to delete a course.");

		} else if (courseRegister.findCourse(courseCode) == null) {

			addErrorMessage("The course code " + courseCode + " does not exist. Please try again!");
			txtCourseCode.clear();

		} else {

			//Alert pop up window
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Please confirm");
			alert.setHeaderText("Are you sure you want to remove course " + courseCode + "?");

			ButtonType buttonTypeYes = new ButtonType("Yes");
			ButtonType buttonTypeNo = new ButtonType("No", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == buttonTypeYes){

				//Remove Course from courseRegister
				courseRegister.removeCourse(txtCourseCode.getText());

				//Clear TextArea
				txtCourseCode.clear();
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//TableViews
		columnCourseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
		columnCourseName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnCourseCredits.setCellValueFactory(new PropertyValueFactory<>("credits"));
		tableCourse.setItems(courseRegister.getCourseRegister());

		//ChoiceBoxes
		ObservableList<Integer> choiceOfCredits = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 20, 25, 30);
		choiceBoxCredits.setItems(choiceOfCredits);

	}
}