package model;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class WrittenExam {
	
	//Instance variables
	private String examID;
	private String date;
	private String location;
	private String time;
	private static int startNbr = 10000; 
	private static final int MAX_NBR = 99999; 
	private static final int MAX_POINTS = 100;
	private Course course;
	private ObservableList<Result> resultRegister = FXCollections.observableArrayList();

	//Constructors
	public WrittenExam(String date, String location, String time, Course course) {
		examID = "E" + Integer.toString(startNbr);
		startNbr++;

		this.date = date;
		this.location = location;
		this.time = time;
		this.course = course;
	}
	public WrittenExam(String examID, String date, String location, String time, Course course) {
		this.examID = examID;
		this.date = date;
		this.location = location;
		this.time = time;
		this.course = course;
	}
	//Setters and Getters
	public String getExamID() {
		return examID;
	}
	public void setExamID(String examID) {
		this.examID = examID;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;				
	}	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public static int getStartNbr() {
		return startNbr;
	}
	public static int getMaxNbr() {
		return MAX_NBR;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public ObservableList<Result> getResultRegister() {
		return resultRegister;
	}
	public static int getMAX_POINTS() {
		return MAX_POINTS;
	}
	//Methods
	public void addResult(Result result) {			
		resultRegister.add(result);
	}
	public Result findResult(String resultID) {
		for (Result result: resultRegister) {
			if (result.getResultID().equals(resultID)) {	
			}
			return result;
		}
		return null;
	}
	public Result findStudentResult(Student student) {				
		for (Result result: resultRegister) {
			if (result.getStudent().equals(student)) {
				return result;
			}	
		}
		return null;
	}
	public Result removeResult(Result result) {
		resultRegister.remove(result);
		
		return result;
	}

	public int getAverageResult() {
		double sumResults = 0;

		for (Result result: resultRegister) {
			sumResults += result.getResult();
		}
		double average = sumResults / resultRegister.size();

		//Rounds up and casts to int
		int averageInt = (int) Math.round(average);
		
		return averageInt;
	}

	public int getMedianResult() {	
		int[] resultList = new int[getResultRegister().size()];
		int i = 0;

		//Create an array with all results
		for (Result result: getResultRegister()) {
			resultList[i] = result.getResult();
			i++;
		}
		//Sorts array in ascending order
		Arrays.sort(resultList);

		//If array length is even
		if (resultList.length % 2 == 0) {		
			//The two middle test results gets added together and then divided by 2.
			double sumOfMiddle = resultList[resultList.length / 2] + resultList[resultList.length / 2 - 1];
			double median = sumOfMiddle/ 2;
			//Rounds up and casts to int
			int medianInt = (int) Math.round(median);
			return medianInt;

			//If array length is odd
		} else {											
			int median = resultList[resultList.length / 2];
			return median;
		}
	}

	public int getNumberOfPassedExams() {
		int passedExams = 0;

		for (Result r: resultRegister) {
			if ('F' != r.getLetterGrade()) {
				passedExams++;
			}
		}
		return passedExams;
	}
	public String toString() {						//This converts WrittenExam to name
		return examID;
	}	
}