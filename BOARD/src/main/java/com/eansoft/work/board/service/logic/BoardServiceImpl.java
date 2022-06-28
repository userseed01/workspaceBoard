package com.eansoft.work.board.service.logic;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eansoft.work.board.domain.Board;
import com.eansoft.work.board.service.BoardService;
import com.eansoft.work.board.store.BoardStore;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardStore bStore;
	
	@Autowired
	private SqlSession sqlSession;
	
	// 게시판 메인 화면
	@Override
	public List<Board> printAllBoard() {
		List<Board> bList = bStore.selectAllMain(sqlSession);
		return bList;
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
}
