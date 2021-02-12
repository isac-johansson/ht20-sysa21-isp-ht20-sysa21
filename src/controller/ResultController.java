
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
import model.Result;
import model.Student;
import model.StudentRegister;
import model.WrittenExam;
import model.WrittenExamRegister;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class ResultController implements Initializable {

	@FXML
	private TableColumn<Result, String> columnResultCourseCode;						
	@FXML
	private TableColumn<Result, String> columnResultWrittenExamID;				
	@FXML
	private TableColumn<Result, String> columnResultStudentID;					
	@FXML
	private TableColumn<Result, Integer> columnResultResult;						
	@FXML
	private TableColumn<Result, Character> columnResultGrade;						
	@FXML
	private TableColumn<Result, String> columnResultResultID;						
	@FXML
	private TableView<Result> tableResult;											
	@FXML
	private TextField txtResultID;
	@FXML
	private ChoiceBox<WrittenExam> choiceBoxAddWrittenExam;
	@FXML
	private ChoiceBox<Student> choiceBoxResultStudent;
	@FXML
	private ChoiceBox<Integer> choiceBoxResult;
	@FXML
	private ChoiceBox<WrittenExam> choiceBoxShowWrittenExam;

	//Connects registers in Main
	private StudentRegister studentRegister = Main.getStudentRegister();
	private WrittenExamRegister writtenExamRegister = Main.getWrittenExamRegister();

	//Error message method
	private void addErrorMessage(String errorMessage) {
		
	Alert alert = new Alert(AlertType.ERROR);
	alert.setTitle("Error");
	alert.setHeaderText("Something went wrong");
	alert.setContentText(errorMessage);
	alert.showAndWait();
	}
	
	//Menu button
	@FXML
	private AnchorPane anchorPane;

	@FXML
	private void loadWelcomeView(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/WelcomeView.fxml")); 

		anchorPane.getChildren().setAll(pane);
	}

	//Result buttons

	//Add Result button
	@FXML
	public void addResult(ActionEvent event) {

		Student student = choiceBoxResultStudent.getValue();
		WrittenExam writtenExam = choiceBoxAddWrittenExam.getValue();
		Integer resultInt = choiceBoxResult.getValue();

		if (student == null && writtenExam == null && resultInt == null) {

			addErrorMessage("Please select an exam, a student and a result.");

		} else if (student != null && writtenExam == null && resultInt == null) {

			addErrorMessage("Please select an exam and a result.");

		} else if (student != null && writtenExam != null && resultInt == null) {

			addErrorMessage("Please select a result.");

		} else if (student == null && writtenExam != null && resultInt == null) {

			addErrorMessage("Please select a student and a result.");

		} else if (student == null && writtenExam != null && resultInt != null) {

			addErrorMessage("Please select a student.");

		} else if (student == null && writtenExam == null && resultInt != null) {

			addErrorMessage("Please select an exam and a student.");

		} else if (student != null && writtenExam == null && resultInt != null) {

			addErrorMessage("Please select an exam.");

		} else if ((writtenExam.findStudentResult(student)) != null) {

			addErrorMessage("Ops. There is alredy a result registered for exam " + writtenExam + " and student " + student + "!");

			//Clear ChoiceBoxes
			choiceBoxAddWrittenExam.setValue(null);
			choiceBoxResultStudent.setValue(null);
			choiceBoxResult.setValue(null);
			
		} else {

			Result result = new Result(resultInt, student, writtenExam);

			writtenExam.addResult(result);						
			student.addResult(result);

			//Clear ChoiceBoxes
			choiceBoxAddWrittenExam.setValue(null);
			choiceBoxResultStudent.setValue(null);
			choiceBoxResult.setValue(null);
		}
	}
	//Remove result button
	@FXML
	public void removeResult(ActionEvent event) {

		String resultID = txtResultID.getText();
		Result examResult = writtenExamRegister.findResult(resultID);

		if (resultID.trim().isEmpty()) {

			addErrorMessage("You need to type in a result ID to delete a result. Please try again!");

		} else if (examResult == null) {

			addErrorMessage("The result ID " + resultID + " does not exist. Please try again!");

			txtResultID.clear();

		} else {

			//Alert pop up window
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Please confirm");
			alert.setHeaderText("Are you sure you want to remove result " + resultID + "?");

			ButtonType buttonTypeYes = new ButtonType("Yes");
			ButtonType buttonTypeNo = new ButtonType("No", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == buttonTypeYes){

				WrittenExam writtenExam = examResult.getWrittenExam();

				//Remove result from writtenExam
				writtenExam.removeResult(examResult);
				//Remove result from Student
				examResult.getStudent().removeResult(examResult);											

				//Clear TextArea
				txtResultID.clear();
			}
		}
	}
	//showWrittenExamResult button
	@FXML
	public void showWrittenExamResult(ActionEvent event) {						

		WrittenExam choiceBoxWrittenExam = choiceBoxShowWrittenExam.getValue();

		if (choiceBoxWrittenExam == null) {

			addErrorMessage("You need to select a course. Please try again!");

		} else if (choiceBoxWrittenExam.getResultRegister().isEmpty()){

			addErrorMessage(choiceBoxWrittenExam + " does not have any registered results. Please select another exam!");

			//Clear TableView
			//		tableResult.getItems().clear();										//Fixa?

			//Clear ChoiceBox
			choiceBoxShowWrittenExam.setValue(null);

		} else {

			ObservableList<Result> resultListWrittenExam = choiceBoxWrittenExam.getResultRegister();
			tableResult.setItems(resultListWrittenExam);

			//Clear ChoiceBox
			//choiceBoxResultWrittenExam2.setValue(null);
			//Clear TextArea
		}
	}	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//TableViews

		//tableResult
		tableResult.setPlaceholder(new Label("Select an exam to show results."));

		columnResultWrittenExamID.setCellValueFactory(new PropertyValueFactory<>("writtenExam"));		
		columnResultStudentID.setCellValueFactory(new PropertyValueFactory<>("student"));		
		columnResultResultID.setCellValueFactory(new PropertyValueFactory<>("resultID"));	
		columnResultResult.setCellValueFactory(new PropertyValueFactory<>("result"));		
		columnResultGrade.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));

		//ChoiceBoxes

		ObservableList<Student> choiceOfStudent = FXCollections.observableArrayList(studentRegister.getStudentRegister()); 
		choiceBoxResultStudent.setItems(choiceOfStudent);

		ObservableList<WrittenExam> choiceOfResultWrittenExam = FXCollections.observableArrayList(writtenExamRegister.getWrittenExamRegister()); 
		choiceBoxAddWrittenExam.setItems(choiceOfResultWrittenExam);

		ObservableList<Integer> choiceOfResult = FXCollections.observableArrayList();
		//Adds numbers from 0 to MAX_POINTS (from class WrittenExam) to the ObservableList
		for (int i = 0; i <= WrittenExam.getMAX_POINTS(); i++) {
			choiceOfResult.add(i);
		}
		choiceBoxResult.setItems(choiceOfResult);

		//	ObservableList<WrittenExam> choiceOfResultWrittenExam2 = FXCollections.observableArrayList(writtenExamRegister.getWrittenExamRegister()); 
		choiceBoxShowWrittenExam.setItems(choiceOfResultWrittenExam);	
	}
}