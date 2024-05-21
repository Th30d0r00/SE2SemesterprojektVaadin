package org.hbrs.se2.demo.registration;

import org.hbrs.se2.project.hellocar.entities.Company;
import org.hbrs.se2.project.hellocar.entities.Rolle;
import org.hbrs.se2.project.hellocar.entities.Student;

import java.util.List;

public class UserDTO {

	private int id;
	private String userid;
	private String password;
	private String email;
	private List<Rolle> roles;
	private Student studentenInfos;
	private Company companyInfos;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserID() {
		return userid;
	}
	public void setUserID(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<Rolle> getRolle() {
		return roles;
	}
	public void setRolle(List<Rolle> roles) {
		this.roles = roles;
	}
	public Student getStudentenInfos() {
		return studentenInfos;
	}
	public void SetStudentenInfos(Student studentenInfos) {
		this.studentenInfos = studentenInfos;
	}
	public Company getCompanyInfos() {
		return companyInfos;
	}
	public void SetCompanyInfos(Company companyInfos) {
		this.companyInfos = companyInfos;
	}

	@Override
	public String toString() {
		return "UserDTO [userID=" + userid + ", email=" + email + ", rollen=" + roles +"]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userid == null) {
			if (other.userid != null)
				return false;
		} else if (!userid.equals(other.userid))
			return false;
		return true;
	}

}
