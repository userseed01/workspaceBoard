package com.eansoft.work.board.store.logic;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.eansoft.work.board.domain.Board;
import com.eansoft.work.board.store.BoardStore;

@Repository
public class BoardStoreLogic implements BoardStore{

	// 게시판 메인 화면
	@Override
	public List<Board> selectAllMain(SqlSession sqlSession) {
		List<Board> bList = sqlSession.selectList("BoardMapper.selectAllBoard");
		return bList;
	}
}