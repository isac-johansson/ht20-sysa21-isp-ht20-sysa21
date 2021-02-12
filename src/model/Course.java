package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Course {
	private String courseCode;
	private static int startNbr = 10000; 
	private static final int MAX_NBR = 99999; 
	private String name;
	private int credits;
	private ObservableList<WrittenExam> writtenExamRegister = FXCollections.observableArrayList();
	
	//Contructor
	public Course(String courseCode, String name, int credits) {
		this.courseCode = courseCode;
		this.name = name;
		this.credits = credits;
	}
	public Course(String name, int credits) {
		courseCode = "C" + Integer.toString(startNbr);
		startNbr++;
		this.name = name;
		this.credits = credits;
	}
	
	//Setters and getters
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public static int getStartNbr() {
		return startNbr;
	}
	public static int getMaxNbr() {
		return MAX_NBR;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
	public ObservableList<WrittenExam> getWrittenExam() {
		return writtenExamRegister;
	}
	
	//Methods
	public void addWrittenExam(WrittenExam writtenExam) {
		writtenExamRegister.add(writtenExam);
	}
	public WrittenExam removeWrittenExam(String examID) {
		for (WrittenExam writtenExamTmp : writtenExamRegister) {
			if (writtenExamTmp.getExamID().equals(examID)) {
				
				writtenExamRegister.remove(writtenExamTmp);
				return writtenExamTmp;
			}
		}
		return null;
	}

	public WrittenExam findWrittenExam(String examID) {
		for (WrittenExam writtenExamTmp : writtenExamRegister) {
			if (writtenExamTmp.getExamID().equals(examID)) {
				return writtenExamTmp;
			}
		}
		return null;
	}
	public String toString() {						//This converts Course to name
		return courseCode;
	}	
}
