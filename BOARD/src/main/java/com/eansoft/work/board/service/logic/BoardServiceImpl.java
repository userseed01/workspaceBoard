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
}
