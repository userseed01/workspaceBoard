package com.eansoft.work.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.eansoft.work.board.domain.Board;
import com.eansoft.work.board.domain.Comment;
import com.eansoft.work.board.domain.File;
import com.eansoft.work.board.domain.PageCount;
import com.eansoft.work.board.service.BoardService;
import com.eansoft.work.common.Pagination;
import com.eansoft.work.common.SaveAttachedFile;
import com.eansoft.work.common.Search;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
		// 조회수 증가
		int result = bService.viewCount(boardNo);
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
	public String boardWrite(Model model, @ModelAttribute File file, @ModelAttribute Board board
			// 첨부파일
			, HttpServletRequest request,
			@RequestParam(value = "uploadFile", required = false) List<MultipartFile> uploadFile) { // 파일 가져올 때
		try {
			int result = bService.boardWrite(board); // 성공 실패 여부는 보통 int
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
	
	// 게시글 상세조회 시 댓글 조회 화면
	@ResponseBody
	@RequestMapping(value="/board/boardCommentView.eansoft", method= RequestMethod.GET, produces = "application/json;charset=utf-8")
	public String boardComment(@RequestParam("boardNo") int boardNo) {
		List<Comment> cList = bService.printAllComment(boardNo);
		if (!cList.isEmpty()) {
			// 날짜형식을 우리나라 표기법으로 변환 (데이터 포맷을 변경)
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			return gson.toJson(cList);
		}
			return null;
	}
	
	// 게시글 상세조회 시 댓글 등록
	@ResponseBody
	@RequestMapping(value="/board/boardCommentAdd.eansoft", method = RequestMethod.POST)
	public String boardCommentAdd(@ModelAttribute Comment comment, HttpServletRequest request) { // http는 로그인할때 세션에 담아둔 값 가져오고싶을때 사용
		HttpSession session = request.getSession();
		String emplId = (String) session.getAttribute("emplId"); // 변수에 저장된 emplId를 가져오겟다, 세션은 String으로 저장 안돼서 형변환
		comment.setEmplId(emplId); // reply에 id를 넣겟다
		int result = bService.addComment(comment);
		if (result > 0) {
			return "success";
		} else {
			return "fail";
		}
	}
	
	// 게시글 상세 조회 시 댓글 수정
	@ResponseBody
	@RequestMapping(value="/board/boardCommentModify.eansoft", method = RequestMethod.POST)
	public String boardCommentModify(@ModelAttribute Comment comment) {
		int result = bService.modifyComment(comment);
		if(result > 0) {
			return "success";
		} else {
			return "fail";
		}
	}
	
	// 게시글 상세 조회 시 댓글 삭제
	@ResponseBody
	@RequestMapping(value="/board/boardCommentRemove.eansoft", method = RequestMethod.GET)
	public String boardCommentRemove(@RequestParam("commentNo") int commentNo) {
		int result = bService.removeComment(commentNo);
		if(result > 0) {
			return "success";
		} else {
			return "fail";
		}
	}
	
	// 게시글 상세 조회 시 댓글의 답글 등록
	@ResponseBody
	@RequestMapping(value="/board/recommentAdd.eansoft", method = RequestMethod.POST)
	public String recommentAdd(@ModelAttribute Comment comment, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
	        String emplId = (String) session.getAttribute("emplId");
	        comment.setEmplId(emplId);
	        int result = bService.recommentAdd(comment);
	        if(result > 0) {
	        	return "success";
	        } else {
	        	return "fail";
	        }
		} catch (Exception e) {
			return e.toString();
		}
	}
	
	// 엑셀 파일 읽기(POI, Poor Obfuscation Implementation)
	// Apache POI는 아파치 소프트웨어 재단에 의해 운영되는 오픈소스 프로젝트 입니다.
	// 순수 자바 라이브러리로서 Microsoft Office의 Word, PowerPoint, Excel 형식의 파일을 읽고 쓸 수 있게 해주며
	// 최근의 오피스 포맷인 Office Open XML File Format도 지원해줍니다.
	
	// 1. FileInputStream fis = new FileInputStream("엑셀파일경로"); // 파일 읽기
	// 2. HSSFWorkbook workbook = new HSSFWorkbook(fis); //엑셀파일을 관리하는 객체로 받아오기
	// 3. HSSFSheet sheet = workbook.getSheet(0); // 인덱스로 원하는 시트 가져오기
	// 4. HSSFCell cell = sheet.getRow(x).getCell(y); // 해당 시트에서 x행 y열의 셀 가져오기
	// 5. cell.getCellType() 메서드로 타입확인 후 타입별로 데이터 받기 (NumberCell, StringCell, BooleanCell등..)
	// 엑셀 파일 쓰기
	// 1. HSSFWorkbook workbook = new HSSFWorkbook(); // 새 엑셀 파일 만들기
	// 2. HSSFSheet sheet = workbook.createSheet(); // 엑셀 워크북에서 새 시트 만들기
	// 3. HSSFRow row = sheet.createRow(x); // x행에 만들기(접근)
	// 4. HSSFCell cell = row.createCell(y); // 해당 행의 y열 셀 만들기(접근)
	// 5. cell.setCellValue(값); // 접근한 셀에 값 입력하기
	// 6. FileOutputStream fos = new FileOutputStream("만든엑셀파일경로");
	// 7. workbook.write(fos); //파일 출력하기
	
	// 게시판 내용 전체 다운로드(POI, void는 리턴 없을 때 사용)
	// 응답에 관한 부분은 reponse 객체에 담고
	// 요청에 들어 온 것은 request가 담고 있으니 내가 꺼내올 정보들은 request에서 꺼내면 됨
	@RequestMapping(value="/board/boardDownload.eansoft", method = RequestMethod.GET)
	public void downloadExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Workbook workbook = new HSSFWorkbook(); //  새 엑셀 파일 만들기, 엑셀파일을 관리하는 객체로 받아오기
		Sheet sheet = workbook.createSheet("게시판내용들"); // 엑셀 워크북에서 새 시트 만들기
		int rowNo = 0;
		Row headerRow = sheet.createRow(rowNo++); // x행에 만들기(접근)
		headerRow.createCell(0).setCellValue("글 번호");
	    headerRow.createCell(1).setCellValue("글 종류");
	    headerRow.createCell(2).setCellValue("글 제목");
	    headerRow.createCell(3).setCellValue("첨부파일 개수");
	    headerRow.createCell(4).setCellValue("작성자");
	    headerRow.createCell(5).setCellValue("작성일");
	    headerRow.createCell(6).setCellValue("조회수");
		List<Board> bList = bService.printAllBoard(); // 위에 게시판 메인과 같게 적기
		for(Board board : bList) {
			Row row = sheet.createRow(rowNo++); // x행에 만들기(접근)
			row.createCell(0).setCellValue(board.getBoardNo());
			row.createCell(1).setCellValue(board.getBoardType());
			row.createCell(2).setCellValue(board.getBoardTitle());
			row.createCell(3).setCellValue(board.getFileCount());
			row.createCell(4).setCellValue(board.getEmplId());
			row.createCell(5).setCellValue(board.getBoardDate());
			row.createCell(6).setCellValue(board.getBoardHits());
		}
		response.setContentType("ms-vnd/excel");
	    response.setHeader("Content-Disposition", "attachment;filename=boardList.xls");
	    // 밑에 두줄 throws IOException 추가해줘야 오류 안뜸
	    workbook.write(response.getOutputStream());
	    workbook.close(); // 리소스 자동닫기
	}
}