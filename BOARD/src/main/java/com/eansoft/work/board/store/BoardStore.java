package com.eansoft.work.board.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.eansoft.work.board.domain.Board;
import com.eansoft.work.board.domain.PageCount;

public interface BoardStore {

	// 게시판 메인 화면
	public List<Board> selectAllMain(SqlSession sqlSession, PageCount pc);

	// 게시글 상세조회 화면
	public Board selectDetailBoard(SqlSession sqlSession, Integer boardNo);

	// 게시글 작성
	public int insertBoard(SqlSession sqlSession, Board board);

	// 게시글 수정
	public int updateBoard(SqlSession sqlSession, Board board);

	// 게시글 삭제 화면
	public int deleteBoard(SqlSession sqlSession, int boardNo);

	// 게시판 메인 페이징
	public int selectListCount(SqlSession sqlSession);
}