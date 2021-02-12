package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;

public class WelcomeController {
	//Welcome view
	@FXML
	private AnchorPane anchorPane;
	
	//Navigates to StudentView
	@FXML
	private void loadStudentView(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/StudentView.fxml")); 
		
		anchorPane.getChildren().setAll(pane);
	}
	//Navigates to CourseView
	@FXML
	private void loadCourseView(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/CourseView.fxml")); 
		
		anchorPane.getChildren().setAll(pane);
	}
	//Navigates to WrittenExamView
	@FXML
	private void loadWrittenExamView(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/WrittenExamView.fxml")); 
		
		anchorPane.getChildren().setAll(pane);
	}
	//Navigates to ResultView
	@FXML
	private void loadResultView(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/ResultView.fxml")); 
		
		anchorPane.getChildren().setAll(pane);
	}
}
