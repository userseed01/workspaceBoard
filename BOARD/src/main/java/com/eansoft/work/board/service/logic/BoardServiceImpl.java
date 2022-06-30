package com.eansoft.work.board.service.logic;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eansoft.work.board.domain.Board;
import com.eansoft.work.board.domain.PageCount;
import com.eansoft.work.board.service.BoardService;
import com.eansoft.work.board.store.BoardStore;
import com.eansoft.work.common.Search;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardStore bStore;
	
	@Autowired
	private SqlSession sqlSession;
	
	// 게시판 메인 화면
	@Override
	public List<Board> printAllBoard(PageCount pc) {
		List<Board> bList = bStore.selectAllMain(sqlSession, pc);
		return bList;
	}

	// 게시판 메인 페이징
	@Override
	public int getListCount() {
		int result= bStore.selectListCount(sqlSession);
		return result;
	}
	
	// 게시글 상세조회 화면
	@Override
	public Board printDetailBoard(Integer boardNo) {
		Board board = bStore.selectDetailBoard(sqlSession, boardNo);
		return board;
	}

	// 게시글 작성
	@Override
	public int boardWrite(Board board) {
		int result = bStore.insertBoard(sqlSession, board);
		return result;
	}

	// 게시글 수정
	@Override
	public int boardModify(Board board) {
		int result = bStore.updateBoard(sqlSession, board);
		return result;
	}

	// 게시글 삭제 화면
	@Override
	public int boardDelete(int boardNo) {
		int result = bStore.deleteBoard(sqlSession, boardNo);
		return result;
	}
	
	// 게시판 검색 화면
	@Override
	public List<Board> printSearchBoard(Search search, PageCount pc) {
		List<Board> bList = bStore.selectSearchBoard(sqlSession, search, pc);
		return bList;
	}

	// 게시판 검색 페이징
	@Override
	public int boardSearchListCount(Search search) {
		int result = bStore.selectSearchCount(sqlSession, search);
		return result;
	}
}