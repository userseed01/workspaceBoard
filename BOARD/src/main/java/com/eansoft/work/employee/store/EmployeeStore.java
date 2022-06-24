package com.eansoft.work.employee.store;

import org.apache.ibatis.session.SqlSession;

import com.eansoft.work.employee.domain.Employee;

public interface EmployeeStore {

	// 회원가입
	public int insertEmployee(SqlSession sqlSession, Employee employee);

	// 로그인
	public Employee selectLoginEmployee(SqlSession sqlSession, Employee employee);

}