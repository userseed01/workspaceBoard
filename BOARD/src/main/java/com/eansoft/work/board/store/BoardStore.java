package com.eansoft.work.board.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.eansoft.work.board.domain.Board;

public interface BoardStore {

	// 게시판 메인 화면
	public List<Board> selectAllMain(SqlSession sqlSession);

	// 게시글 상세조회
	public Board selectDetailBoard(SqlSession sqlSession, Integer boardNo);

}