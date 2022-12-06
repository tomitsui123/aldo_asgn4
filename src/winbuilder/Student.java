package winbuilder;

import java.util.ArrayList;

public class Student {
	
	private String name;
	private String sid;
	private ArrayList<Course> courses;
	
	public Student(String name, String sid) {
		this.name = name;
		this.sid = sid;
		courses = new ArrayList<Course>();
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
	
	public Object[] getCourses() {
		return this.courses.toArray();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Student)) {
			return false;
			}
	      Student student = (Student)obj;
	      return this.sid.equals(student.getSid());
   }
	@Override
	public String toString() {
		if (this.name == "") {
			return "";
		}
		return this.name + "(" + this.sid + ")";
	}

	public void addCourse(Course course) {
		// TODO Auto-generated method stub
		courses.add(course);
		
	}
}
