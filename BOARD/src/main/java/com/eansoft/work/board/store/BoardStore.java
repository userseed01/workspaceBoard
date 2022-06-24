package com.eansoft.work.board.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.eansoft.work.board.domain.Board;

public interface BoardStore {

	// 게시판 메인 화면
	public List<Board> selectAllMain(SqlSession sqlSession);

}
