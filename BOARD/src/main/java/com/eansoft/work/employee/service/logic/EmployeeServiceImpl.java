package com.eansoft.work.employee.service.logic;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eansoft.work.employee.domain.Employee;
import com.eansoft.work.employee.service.EmployeeService;
import com.eansoft.work.employee.store.EmployeeStore;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeStore eStore;

	@Autowired
	private SqlSession sqlSession;
	
	// 여기에서 위아랫줄 변수 바꿔줌
	
	// 회원가입
	@Override
	public int registerEmployee(Employee employee) {
		int result = eStore.insertEmployee(sqlSession, employee);
		return result;
	}

	// 로그인
	@Override
	public Employee loginMember(Employee employee) {
		Employee employeeOne = eStore.selectLoginEmployee(sqlSession, employee);
		return employeeOne;
	}

}
