package com.eansoft.work.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.eansoft.work.board.domain.Board;
import com.eansoft.work.board.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService bService;

	// get - 조회(select)
	// post- 생성(insert),수정(update),삭제(delete)

	// 게시판 메인 화면
	@RequestMapping(value = "/board/boardMainView.eansoft", method = RequestMethod.GET)
	public ModelAndView boardMainView(ModelAndView mv) {
		try {
			List<Board> bList = bService.printAllBoard();
			if (!bList.isEmpty()) {
				mv.addObject("bList", bList); // 담겨있는 값을 jsp로 넘겨 리스트를 쓸 수 있게
				mv.setViewName("board/boardMain");
			} else {
				mv.addObject("msg", "게시판 조회 실패");
				mv.setViewName("common/errorPage");
			}
		} catch (Exception e) {
			mv.addObject("msg", e.toString());
			mv.setViewName("common/errorPage");
		}
		return mv;
	}

	// 게시글 상세조회 화면
	@RequestMapping(value = "/board/boardDetailView.eansoft", method = RequestMethod.GET)
	public String boardDetailView(Model model, @RequestParam("boardNo") Integer boardNo) {
		Board board = bService.printDetailBoard(boardNo);
		if (board != null) { // 값을 담아와야해서 int를 쓸 수 없음
			model.addAttribute("board", board);
			return "board/boardDetail";
		} else {
			model.addAttribute("msg", "게시글 조회 실패");
			return "common/errorPage";
		}
	}

	// 게시글 작성 화면
	@RequestMapping(value = "/board/boardWriteView.eansoft", method = RequestMethod.GET)
	public String boardWriteView() {
		return "board/boardWrite";
	}

	// 게시글 작성
	@RequestMapping(value = "/board/boardWrite.eansoft", method = RequestMethod.POST)
	public String boardWrite(Model model, @ModelAttribute Board board) {
		int result = bService.boardWrite(board); // 성공 실패 여부는 보통 int
		if (result > 0) {
			return "redirect:/board/boardMainView.eansoft"; // url, 그냥 return은 jsp이름
		} else {
			model.addAttribute("msg", "게시글 등록 실패");
			return "common/errorPage";
		}
	}

	// 게시글 수정 화면
	@RequestMapping(value = "/board/boardModifyView.eansoft", method = RequestMethod.GET)
	public String boardModifyView() {
		return "board/boardModify";
	}

	// 게시글 수정
	@RequestMapping(value="/board/boardModify.eansoft", method = RequestMethod.POST)
	public ModelAndView boardModify(ModelAndView mv, @ModelAttribute Board board) {
		
		
		
		
		
		return mv;
		
	
	
	
	
	
	
	
	
	
	
}

}