package winbuilder;

public class Student {
	
	private String name;
	private String sid;
	private String cCode;
	private String cTitle;
	private String grade;
	
	public Student(String name, String sid, String cCode, String cTitle, String grade) {
		this.name = name;
		this.sid = sid;
		this.cCode = cCode;
		this.cTitle = cTitle;
		this.grade = grade;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getcCode() {
		return cCode;
	}

	public void setcCode(String cCode) {
		this.cCode = cCode;
	}

	public String getcTitle() {
		return cTitle;
	}

	public void setcTitle(String cTitle) {
		this.cTitle = cTitle;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String toString() {
		if (this.name == "") {
			return "";
		}
		return this.name + "(" + this.sid + ")";
	}
}
