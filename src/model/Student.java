package model;

import java.util.ArrayList;

public class Student {
	
	//Instance variables
	private String studentID;
	private String name;
	private static int startNbr = 10000; 
	private static final int MAX_NBR = 99999; 
	private ArrayList<Result> resultRegister = new ArrayList<Result>();
	
	//Constructor
	public Student(String studentID, String name) {
		this.studentID = studentID;
		this.name = name;
	}
	public Student(String name) {
		studentID = "S" + Integer.toString(startNbr);
		startNbr++;
		this.name = name;
	}
	
	//Setters and Getters
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public String getName() {
		return name;
	}
	public static int getStartNbr() {
		return startNbr;
	}
	public static int getMaxNbr() {
		return MAX_NBR;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Result> getResultRegister() {
		return resultRegister;
	}
	
	//Methods
	public void addResult(Result result) {
		resultRegister.add(result);
	}
	public Result removeResult(Result result) {
		resultRegister.remove(result);
		return result;
	}
	public Result findResult(String resultID) {
		for (Result result : resultRegister) {
			if (result.getResultID().equals(resultID)) {
				return result;
			}
		}
		return null;
	}
	public String toString() {						//This converts Student to studentID
		return studentID;
	}	
}
