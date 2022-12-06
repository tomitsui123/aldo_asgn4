package winbuilder;

public class Course {
	private String cCode;
	private String cTitle;
	private Grade grade;
	public Course(String cCode, String cTitle, Grade grade) {
		this.cCode = cCode;
		this.cTitle = cTitle;
		this.grade = grade;
	}
	public String getCourseCode() {
		return this.cCode;
	}
	
	public String getCourseTitle() {
		return this.cTitle;
	}
	public Grade getCourseGrade() {
		return this.grade;
	}
	public String toString() {
		return this.cCode + " - " + this.cTitle;
	}
}
