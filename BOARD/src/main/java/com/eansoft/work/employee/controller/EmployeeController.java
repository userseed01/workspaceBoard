package com.eansoft.work.employee.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.eansoft.work.employee.domain.Employee;
import com.eansoft.work.employee.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService eService;

	// get - 조회(select)
	// post- 생성(insert),수정(update),삭제(delete)

	// 회원가입 화면
	@RequestMapping(value = "/employee/registerView.eansoft", method = RequestMethod.GET)
	public ModelAndView joinView(ModelAndView mv) {
		mv.setViewName("employee/register");
		return mv;
	}

	// 회원가입
	// @RequestParam -> String, int, date
	// model은 도메인에서 가져옴(set사용위해)
	// param은 register.jsp에서
	// ModalAttribute는 도메인 다 가져올 때
	// RequestParam은 객체 하나만 가져올 때
	// Model은 String
	// ModalAndView는 ModelAndView
	@RequestMapping(value = "/employee/register.eansoft", method = RequestMethod.POST) // jsp와 post 맞춰줌
	public String memberRegister(Model model, @ModelAttribute Employee employee) { // model 결과 보여줄 때 사용(serlvet-context)
		try {
			// int result -> 넘겨줄 때 사용
			int result = eService.registerEmployee(employee); // .뒤는 서비스의 ()로 가겠다
			if (result > 0) { // 1성공
				return "redirect:/";
			} else { // 0실패
				model.addAttribute("msg", "회원가입에 실패했습니다.");
				return "/common/errorPage";
			}
		} catch (Exception e) { // 그 외의 경우 실패
			model.addAttribute("msg", e.toString()); // 에러 메세지를 String으로 보여줘라
			return "/common/errorPage";
		}
	}

	// 로그인 화면 (여기서 -> reg.jsp -> 로그인 컨트롤러 -> 홈)
		@RequestMapping(value = "/home.eansoft", method = RequestMethod.GET)
		public ModelAndView loginView(ModelAndView mv) {
			mv.setViewName("home"); // 셋뷰네임 거기로가라
			return mv;
		}
	
	// 로그인
	// HttpServletRequest -> 경로, 파일저장, 로그인 세션때 사용(web.xml)
	@RequestMapping(value="/employee/login.eansoft", method = RequestMethod.POST)
	public String employeeLogin(Model model, HttpServletRequest request
			, @RequestParam("emplId") String emplId
			, @RequestParam("emplPw") String emplPw) {
		Employee employee = new Employee();
		employee.setEmplId(emplId);
		employee.setEmplPw(emplPw);
		try {
			Employee empLogin = eService.loginMember(employee);
			if (empLogin != null) {
				// 세션에 담기
				HttpSession session = request.getSession();
				session.setAttribute("emplId", empLogin.getEmplId());
				session.setAttribute("emplPw", empLogin.getEmplPw());
				return "redirect:/home.eansoft";
			} else {
				model.addAttribute("msg", "로그인에 실패했습니다.");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", e.toString());
			return "common/errorPage";
		}
	}
	
	// 로그아웃
	// 세션에 넣어줬던 정보 없애기만 하면 돼서 controller만 쓰면 됨
	@RequestMapping(value="/employee/logout.eansoft", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session != null) {
			session.invalidate();
			return "redirect:/";
		} else {
			request.setAttribute("msg", "로그아웃에 실패했습니다.");
			return "common/errorPage";
		}
	}
}