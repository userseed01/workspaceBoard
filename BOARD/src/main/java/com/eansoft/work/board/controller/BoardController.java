package com.eansoft.work.board.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.eansoft.work.board.domain.Board;
import com.eansoft.work.board.domain.File;
import com.eansoft.work.board.domain.PageCount;
import com.eansoft.work.board.service.BoardService;
import com.eansoft.work.common.Pagination;
import com.eansoft.work.common.SaveAttachedFile;
import com.eansoft.work.common.Search;

@Controller
public class BoardController {

	@Autowired
	private BoardService bService;

	// get - 조회(select)
	// post- 생성(insert),수정(update),삭제(delete)
	// Model model, ModelAndView mv -> jsp로 객체넘겨줄때 사용

	// 게시판 메인 화면
	@RequestMapping(value = "/board/boardMainView.eansoft", method = RequestMethod.GET)
	// int, Integer 모두 가능하나 Integer -> 널체크가 가능
	public ModelAndView boardMainView(ModelAndView mv, @RequestParam(value = "page", required = false) Integer page) {
		// 페이징 처리
		int currentPage = (page != null) ? page : 1;
		// 비즈니스 로직 -> DB에서 전체 게시물 갯수 가져옴(총 갯수를 세야함)
		int totalCount = bService.getListCount();
		// 현재 페이지 게시물 갯수, 총 게시물 갯수
		PageCount pc = Pagination.getPageCount(currentPage, totalCount);
		// 비즈니스 로직 -> DB에서 데이터를 가져와야 함
		try {
			// 리스트로 담아줌
			List<Board> bList = bService.printAllBoard(pc);
			if (!bList.isEmpty()) {
				mv.addObject("bList", bList); // 담겨있는 값을 jsp로 넘겨 리스트를 쓸 수 있게
				// 페이징 처리
				mv.addObject("pc", pc);
				mv.addObject("listType", "basicList"); // 검색 페이징 화면 , String으로 넘겨서 "" , b를l로 넘겨
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
		// 조회수 증가
		int result = bService.viewCount(boardNo);
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
	public String boardWrite(Model model, @ModelAttribute File file, @ModelAttribute Board board
			// 첨부파일
			, HttpServletRequest request,
			@RequestParam(value = "uploadFile", required = false) List<MultipartFile> uploadFile) { // 파일 가져올 때
		try {
			if (uploadFile.size() > 0 && !uploadFile.get(0).getOriginalFilename().equals("")) { // 있을때만(없지않으면)
				for (int i = 0; i < uploadFile.size(); i++) { // 가져온 파일의 갯수만큼 계속 db에 쌓음(다중 첨부파일)
					HashMap<String, String> fileMap = SaveAttachedFile.saveFile(uploadFile.get(i), request); // 업로드한 파일 저장하고 경로 리턴
					String filePath = fileMap.get("filePath"); // 실제 파일 저장
					String fileName = fileMap.get("fileName");
					String fileRename = fileMap.get("fileRename");
					if (fileRename != null) { // 파일 데이터(이름만) 저장, 다른 것도 가능(크기, 확장자, ..)
						file.setFilePath(filePath);
						file.setFileName(fileName);
						file.setFileRename(fileRename);
					// 첨부파일
					int fResult = bService.registerBoardFile(file); // result 같은거 한번밖에 못씀
					if( fResult <= 0) {
						model.addAttribute("msg", "첨부파일 등록 실패");
						return "common/errorPage";
					}
					}
				}
			}
			int result = bService.boardWrite(board); // 성공 실패 여부는 보통 int
			if (result > 0) {
				return "redirect:/board/boardMainView.eansoft"; // url, 그냥 return은 jsp이름
			} else {
				model.addAttribute("msg", "게시글 등록 실패");
				return "common/errorPage";
			}
		} catch (Exception e) {
			model.addAttribute("msg", e.toString());
			return "common/errorPage";
		}
	}

	// 게시글 수정 화면(값을 갖고 넘겨줘야 하기 때문에 좀 길어짐)
	// required=false -> required 속성의 default 값은 true
	// false값으로 사용하게 되면 해당 Parameter를 반드시 받지 않아도 됨
	@RequestMapping(value = "/board/boardModifyView.eansoft", method = RequestMethod.GET)
	public String boardModifyView(Model model, @RequestParam(value = "boardNo", required = false) Integer boardNo) {
		try {
			Board board = bService.printDetailBoard(boardNo); // 상세조회한것 처럼 씀
			if (board != null) { // 값을 담아와야해서 int를 쓸 수 없음
				model.addAttribute("board", board);
				return "board/boardModify";
			} else {
				model.addAttribute("msg", "게시글 수정 실패");
				return "common/errorPage";
			}
		} catch (Exception e) {
			return e.toString();
		}
	}

	// 게시글 수정
	@RequestMapping(value = "/board/boardModify.eansoft", method = RequestMethod.POST)
	public String boardModify(@RequestParam("boardNo") int boardNo, @RequestParam("boardTitle") String boardTitle,
			@RequestParam("boardContent") String boardContent) { // 화면에서 정보 갖고옴 리퀘스트파람(보통 인서트, 업데이트때 사용), 수정하려고 써준것
		try {
			Board board = new Board();
			board.setBoardNo(boardNo);
			board.setBoardTitle(boardTitle);
			board.setBoardContent(boardContent);
			// db에 데이터 저장
			int result = bService.boardModify(board);
			if (result > 0) {
				return "redirect:/board/boardMainView.eansoft"; // 리스트이상하게나와서 리다이렉트로 바꿔줌
			} else {
				return "게시글 수정 실패";
			}
		} catch (Exception e) {
			return e.toString();
		}
	}

	// 게시글 삭제 화면
	@RequestMapping(value = "/board/boardDeleteView.eansoft", method = RequestMethod.GET)
	public String boardDelete(@RequestParam(value = "boardNo", required = false) Integer boardNo) {
		try {
			int result = bService.boardDelete(boardNo);
			if (result > 0) {
				return "redirect:/board/boardMainView.eansoft";
			} else {
				return "게시물 삭제 실패";
			}
		} catch (Exception e) {
			return e.toString();
		}
	}

	// 게시판 검색 화면
	@RequestMapping(value = "/board/boardSearchView.eansoft", method = RequestMethod.GET)
	public ModelAndView boardSearch(ModelAndView mv, @ModelAttribute Search search,
			@RequestParam(value = "page", required = false) Integer page) {
		try {
			int currentPage = (page != null) ? page : 1;
			int totalCount = bService.boardSearchListCount(search);
			PageCount pc = Pagination.getPageCount(currentPage, totalCount);
			List<Board> bList = bService.printSearchBoard(search, pc);
			if (!bList.isEmpty()) {
				mv.addObject("bList", bList); // 메인 페이징 화면
				mv.addObject("pc", pc);
				mv.addObject("listType", "searchList"); // 검색 페이징 화면
				mv.setViewName("board/boardMain");
			} else {
				mv.addObject("msg", "검색 실패");
				mv.setViewName("common/errorPage");
			}
		} catch (Exception e) {
			mv.addObject("msg", e.toString());
			mv.setViewName("common/errorPage");
		}
		return mv;
	}
}