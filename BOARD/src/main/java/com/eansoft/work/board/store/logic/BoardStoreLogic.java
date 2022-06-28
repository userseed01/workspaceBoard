package com.eansoft.work.board.store.logic;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.eansoft.work.board.domain.Board;
import com.eansoft.work.board.store.BoardStore;

@Repository
public class BoardStoreLogic implements BoardStore{

	// select는 List & One 둘중에 하나로 적어줘야함
	
	// 게시판 메인 화면
	@Override
	public List<Board> selectAllMain(SqlSession sqlSession) {
		List<Board> bList = sqlSession.selectList("BoardMapper.selectAllBoard");
		return bList;
	}

	// 게시글 상세조회 화면
	@Override
	public Board selectDetailBoard(SqlSession sqlSession, Integer boardNo) {
		Board board = sqlSession.selectOne("BoardMapper.selectDetailBoard", boardNo); // 윗줄 sqlSession 옆에 뭐 있으면 꼭 함께 적어줄것
		return board;
	}

	// 게시글 작성
	@Override
	public int insertBoard(SqlSession sqlSession, Board board) {
		int result = sqlSession.insert("BoardMapper.insertWriteBorad", board);
		return result;
	}

	// 게시글 수정
	@Override
	public int updateBoard(SqlSession sqlSession, Board board) {
		int result = sqlSession.update("BoardMapper.updateBoard", board);
		return result;
	}
}