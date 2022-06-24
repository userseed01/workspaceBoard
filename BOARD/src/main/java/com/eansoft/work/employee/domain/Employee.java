package com.eansoft.work.employee.domain;

import java.sql.Date;

public class Employee {
	
	private String emplId;
	private String emplPw;
	private String emplName;
	private Date emplBirthday;
	private String emplPhone;
	private String emplGender;
	
	public Employee() {}

	public Employee(String emplId, String emplPw, String emplName, Date emplBirthday, String emplPhone,
			String emplGender) {
		super();
		this.emplId = emplId;
		this.emplPw = emplPw;
		this.emplName = emplName;
		this.emplBirthday = emplBirthday;
		this.emplPhone = emplPhone;
		this.emplGender = emplGender;
	}

	public String getEmplId() {
		return emplId;
	}

	public void setEmplId(String emplId) {
		this.emplId = emplId;
	}

	public String getEmplPw() {
		return emplPw;
	}

	public void setEmplPw(String emplPw) {
		this.emplPw = emplPw;
	}

	public String getEmplName() {
		return emplName;
	}

	public void setEmplName(String emplName) {
		this.emplName = emplName;
	}

	public Date getEmplBirthday() {
		return emplBirthday;
	}

	public void setEmplBirthday(Date emplBirthday) {
		this.emplBirthday = emplBirthday;
	}

	public String getEmplPhone() {
		return emplPhone;
	}

	public void setEmplPhone(String emplPhone) {
		this.emplPhone = emplPhone;
	}

	public String getEmplGender() {
		return emplGender;
	}

	public void setEmplGender(String emplGender) {
		this.emplGender = emplGender;
	}

	@Override
	public String toString() {
		return "Employee [emplId=" + emplId + ", emplPw=" + emplPw + ", emplName=" + emplName + ", emplBirthday="
				+ emplBirthday + ", emplPhone=" + emplPhone + ", emplGender=" + emplGender + "]";
	}
}