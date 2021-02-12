
package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Course;
import model.CourseRegister;
import model.WrittenExam;
import model.WrittenExamRegister;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;

public class WrittenExamController implements Initializable {

	@FXML
	private TableView<WrittenExam> tableWrittenExam;
	@FXML
	private TableColumn<WrittenExam, String> columnExamID;
	@FXML
	private TableColumn<WrittenExam, String> columnDate;
	@FXML
	private TableColumn<WrittenExam, String> columnTime;
	@FXML
	private TableColumn<WrittenExam, String> columnLocation;
	@FXML
	private TableColumn<WrittenExam, String> columnCourseCodeExam;										
	@FXML
	private TextField txtExamID;
	@FXML
	private ChoiceBox<Course> choiceBoxCourse;
	@FXML
	private ChoiceBox<String> choiceBoxLocation;
	@FXML
	private ChoiceBox<String> choiceBoxTime;
	@FXML
	private ChoiceBox<WrittenExam> choiceBoxWrittenExam;

	@FXML
	private DatePicker datePickerExam;

	//Connects registers in Main

	private CourseRegister courseRegister = Main.getCourseRegister();
	private WrittenExamRegister writtenExamRegister = Main.getWrittenExamRegister();

	//Menu button
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

	//Exam button actions

	//Add WrittenExam button
	@FXML
	public void addWrittenExam(ActionEvent event) {

		Course courseSelected = choiceBoxCourse.getValue();
		LocalDate localDate = datePickerExam.getValue();
		String dateSelected = null;
		String locationSelected = choiceBoxLocation.getValue();
		String timeSelected =  choiceBoxTime.getValue();

		//Cast LocalDate to String
		if (localDate != null) {
			dateSelected = localDate.toString();
		}

		if (courseSelected == null && locationSelected == null && dateSelected == null && timeSelected == null) {

			addErrorMessage("Please select a course, a location, a date and a time.");

		} else if (courseSelected != null && locationSelected == null && dateSelected == null && timeSelected == null) {

			addErrorMessage("Please select a location, a date and a time.");

		} else if (courseSelected != null && locationSelected != null && dateSelected == null && timeSelected == null) {

			addErrorMessage("Please select a date and a time.");

		} else if (courseSelected != null && locationSelected != null && dateSelected != null && timeSelected == null) {

			addErrorMessage("Please select a time.");

		} else if (courseSelected == null && locationSelected != null && dateSelected == null && timeSelected == null) {

			addErrorMessage("Please select a course, a date and a time.");

		} else if (courseSelected == null && locationSelected != null && dateSelected != null && timeSelected == null) {

			addErrorMessage("Please select a course and a time.");

		} else if (courseSelected == null && locationSelected != null && dateSelected != null && timeSelected != null) {

			addErrorMessage("Please select a course.");

		} else if (courseSelected == null && locationSelected == null && dateSelected != null && timeSelected == null) {

			addErrorMessage("Please select a course, a location and a time.");

		} else if (courseSelected == null && locationSelected == null && dateSelected != null && timeSelected != null) {

			addErrorMessage("Please select a course and a location.");

		} else if (courseSelected != null && locationSelected == null && dateSelected != null && timeSelected != null) {

			addErrorMessage("Please select a location.");

		} else if (courseSelected == null && locationSelected == null && dateSelected == null && timeSelected != null) {

			addErrorMessage("Please select a course, a location and a date.");

		} else if (courseSelected != null && locationSelected == null && dateSelected == null && timeSelected != null) {

			addErrorMessage("Please select a location and a date");

		} else if (courseSelected != null && locationSelected != null && dateSelected == null && timeSelected != null) {

			addErrorMessage("Please select a date.");

			//System will only generate IDs between 10000-99999
		} else if (WrittenExam.getStartNbr() > WrittenExam.getMaxNbr()) { 

			addErrorMessage("Sorry but the system has reached its maximum number of unique identifiers assigned. The exam has not been added to the system.");

		} else {

			WrittenExam exam = new WrittenExam(dateSelected, locationSelected, timeSelected, courseSelected);

			courseSelected.addWrittenExam(exam);	
			writtenExamRegister.addWrittenExam(exam);

			//Refresh table
			tableWrittenExam.refresh();

			//Clear TextArea
			txtExamID.clear();  

			//Clear ChoiceBoxes
			choiceBoxCourse.setValue(null);
			datePickerExam.setValue(null);
			choiceBoxLocation.setValue(null);
			choiceBoxTime.setValue(null);

			//Update choiceBox options
			ObservableList<WrittenExam> choiceOfWrittenExam = FXCollections.observableArrayList(writtenExamRegister.getWrittenExamRegister()); 
			choiceBoxWrittenExam.setItems(choiceOfWrittenExam);
		}
	}

	//Update WrittenExam button
	@FXML
	public void updateWrittenExam(ActionEvent event) {

		WrittenExam examSelected = writtenExamRegister.findWrittenExam(txtExamID.getText());
		Course courseSelected = choiceBoxCourse.getValue();
		LocalDate localDate = datePickerExam.getValue();
		String dateSelected = null;
		String locationSelected = choiceBoxLocation.getValue();
		String timeSelected =  choiceBoxTime.getValue();
		String examID = txtExamID.getText();

		//Cast LocalDate to String
		if (localDate != null) {
			dateSelected = localDate.toString();
		}

		//Checks if examID exists. txtExamID.getText() can never be null, it will be contain "" in no input is made. Therefore .trim() is needed.
		if (examID.trim().isEmpty()) {

			addErrorMessage("Please type in an exam ID to update an exam.");

		} else if (writtenExamRegister.findWrittenExam(examID) == null) {

			addErrorMessage("The exam ID " + examID + " does not exist. Please try again!");

			//Clear TextArea
			txtExamID.clear();  

		} else if (courseSelected == null && dateSelected == null && locationSelected == null && timeSelected == null) {

			addErrorMessage("You have not made any selections. Please select a new course, a new location, a new date or a new time to update the course.");

		} else {

			//If statements to accomodate for updating only the selections made
			if (courseSelected != null) {
				examSelected.setCourse(courseSelected);
			}
			if (dateSelected != null) {
				examSelected.setDate(dateSelected);
			}
			if (locationSelected != null) {
				examSelected.setLocation(locationSelected);
			}
			if (timeSelected != null) {
				examSelected.setTime(timeSelected);
			}

			//Refresh table
			tableWrittenExam.refresh();

			//Clear TextArea
			txtExamID.clear();  

			//Clear ChoiceBoxes
			choiceBoxCourse.setValue(null);
			datePickerExam.setValue(null);
			choiceBoxLocation.setValue(null);
			choiceBoxTime.setValue(null);
		}
	}
	//Remove WrittenExam button
	@FXML
	public void removeWrittenExam(ActionEvent event) {

		String examID = txtExamID.getText();

		//Checks if examID exists. txtExamID.getText() can never be null, it will be contain "" if no input is made. Therefore .trim() is needed.
		if (examID.trim().isEmpty()) {

			addErrorMessage("Please type in an exam ID to delete an exam.");

		} else if (writtenExamRegister.findWrittenExam(examID) == null) {

			addErrorMessage("The exam ID " + examID + " does not exist. Please try again!");
			txtExamID.clear();

		} else {

			//Alert pop up window
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Please confirm");
			alert.setHeaderText("Are you sure you want to remove exam " + examID + "?");

			ButtonType buttonTypeYes = new ButtonType("Yes");
			ButtonType buttonTypeNo = new ButtonType("No", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == buttonTypeYes){

				//Remove WrittenExam from Course
				Course course = writtenExamRegister.findWrittenExam(examID).getCourse();
				course.removeWrittenExam(examID);
				//Remove WrittenExam from WrittenExamRegister
				writtenExamRegister.removeWrittenExam(examID);
			
				//Clear TextArea
				txtExamID.clear();
			}
		}
	}
	//Show exam statistics button
	@FXML
	public void showStatistics(ActionEvent event) {

		WrittenExam examSelected = choiceBoxWrittenExam.getValue();

		if (examSelected == null) {

			addErrorMessage("Please select the exam you want statistics from.");

		} else if (examSelected.getResultRegister().isEmpty()) {

			addErrorMessage("Exam " + examSelected.getExamID() + " does not have any registered results.");

		} else {

			String examID = examSelected.getExamID();

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Statistics");
			alert.setHeaderText("Result statistics for exam " + examID);
			alert.setContentText("Average result of all exams: " + examSelected.getAverageResult() + "\n" +  "Median result of all exams: " + examSelected.getMedianResult() + "\n" + "Number of passed exams: " + examSelected.getNumberOfPassedExams());

			alert.showAndWait();

			//Clear ChoiceBoxes
			choiceBoxWrittenExam.setValue(null);
		}
	}

@Override
public void initialize(URL arg0, ResourceBundle arg1) {

	//TableViews

	//tableWrittenExam
	columnExamID.setCellValueFactory(new PropertyValueFactory<>("examID"));
	columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
	columnTime.setCellValueFactory(new PropertyValueFactory<>("time"));
	columnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
	columnCourseCodeExam.setCellValueFactory(new PropertyValueFactory<>("course".toString()));

	tableWrittenExam.setItems(writtenExamRegister.getWrittenExamRegister());

	//ChoiceBoxes

	ObservableList<Course> choiceOfCourse = FXCollections.observableArrayList(courseRegister.getCourseRegister()); 
	choiceBoxCourse.setItems(choiceOfCourse);

	ObservableList<String> choiceOfLocation = FXCollections.observableArrayList("Room A123", "Room A167", "Room B198", "Room B067"); 
	choiceBoxLocation.setItems(choiceOfLocation);

	ObservableList<String> choiceOfTime = FXCollections.observableArrayList("08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00"); 
	choiceBoxTime.setItems(choiceOfTime);

	ObservableList<WrittenExam> choiceOfWrittenExam = FXCollections.observableArrayList(writtenExamRegister.getWrittenExamRegister()); 
	choiceBoxWrittenExam.setItems(choiceOfWrittenExam);

}
}