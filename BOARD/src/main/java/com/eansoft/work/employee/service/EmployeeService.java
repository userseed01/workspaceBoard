package com.eansoft.work.employee.service;

import com.eansoft.work.employee.domain.Employee;

public interface EmployeeService {

	// 회원가입
	public int registerEmployee(Employee employee);

	// 로그인
	public Employee loginMember(Employee employee);

}