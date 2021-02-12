package model;

public class Result {
	private int result;
	private char letterGrade;
	private Student student;
	private WrittenExam writtenExam;
	static private int startNbr = 10000; 
	private String resultID;			
	
	//Constructor
	public Result(int result, Student student, WrittenExam writtenExam) {
		resultID = "R" + Integer.toString(startNbr);
		startNbr++;
		this.setResult(result);
		this.student = student;
		this.writtenExam = writtenExam;	
	}
	
	//Methods
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
		
		if (result >= 85 && result <= 100) {
			this.setLetterGrade('A');
		}
		if (result >= 75 && result <= 84) {
			this.setLetterGrade('B');
		}
		if (result >= 65 && result <= 74) {
			this.setLetterGrade('C');
		}
		if (result >= 55 && result <= 64) {
			this.setLetterGrade('D');
		}
		if (result >= 50 && result <= 54) {
			this.setLetterGrade('E');
		}
		if (result <= 49) {
			this.setLetterGrade('F');
		}
	}
	
	//Setters and Getters
	public char getLetterGrade() {
		return letterGrade;
	}
	public void setLetterGrade(char letterGrade) {
		this.letterGrade = letterGrade;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public WrittenExam getWrittenExam() {
		return writtenExam;
	}
	public void setWrittenExam(WrittenExam writtenExam) {
		this.writtenExam = writtenExam;
	}
	public String getResultID() {										
		return resultID;												
	}																	
	public void setResultID(String resultID) {							
		this.resultID = resultID;										
	}																	
}