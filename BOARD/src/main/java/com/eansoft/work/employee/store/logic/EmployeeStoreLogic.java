package com.eansoft.work.employee.store.logic;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.eansoft.work.employee.domain.Employee;
import com.eansoft.work.employee.store.EmployeeStore;

@Repository
public class EmployeeStoreLogic implements EmployeeStore{

	// 값을 넣어서 수정,삭제,등록 할때는 int
	// 조회하거나 값 담아올때는 list
	
	// 회원가입
	@Override
	public int insertEmployee(SqlSession sqlSession, Employee employee) {
		int result = sqlSession.insert("EmployeeMapper.insertEmployee", employee); // .()값을 mapper id값과 같게 적어줘야함
		return result;
	}

	// 로그인
	@Override
	public Employee selectLoginEmployee(SqlSession sqlSession, Employee employee) {
		Employee employeeOne = sqlSession.selectOne("EmployeeMapper.selectLoginEmployee", employee);
		return employeeOne;
	}
}