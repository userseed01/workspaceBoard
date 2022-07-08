package com.eansoft.work.board.store;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.eansoft.work.board.domain.Board;
import com.eansoft.work.board.domain.Comment;
import com.eansoft.work.board.domain.File;
import com.eansoft.work.board.domain.PageCount;
import com.eansoft.work.common.Search;

public interface BoardStore {

	// 게시판 메인 화면
	public List<Board> selectAllMain(SqlSession sqlSession, PageCount pc);

	// 게시판 메인 페이징
	public int selectListCount(SqlSession sqlSession);
	
	// 게시글 상세조회 화면
	public Board selectDetailBoard(SqlSession sqlSession, Integer boardNo);

	// 게시글 상세조회 화면 조회수 증가
	public int updateViewCount(SqlSession sqlSession, Integer boardNo);
	
	// 게시글 작성
	public int insertBoard(SqlSession sqlSession, Board board);

	// 게시글 작성 시 첨부파일 업로드
	public int insertFile(SqlSession sqlSession, File file);

	// 게시글 수정
	public int updateBoard(SqlSession sqlSession, Board board);

	// 게시글 삭제 화면
	public int deleteBoard(SqlSession sqlSession, int boardNo);

	// 게시판 검색 화면
	public List<Board> selectSearchBoard(SqlSession sqlSession, Search search, PageCount pc);

	// 게시판 검색 페이징
	public int selectSearchCount(SqlSession sqlSession, Search search);

	// 게시글 상세조회 시 댓글 조회 화면
	public List<Comment> selectCommentBoard(SqlSession sqlSession, int boardNo);

	// 게시글 상세조회 시 댓글 등록
	public int insertComment(SqlSession sqlSession, Comment comment);

	// 게시글 상세 조회 시 댓글 수정
	public int updateComment(SqlSession sqlSession, Comment comment);

	// 게시글 상세 조회 시 댓글 삭제
	public int deleteComment(SqlSession sqlSession, int commentNo);

	// 게시글 상세 조회 시 댓글의 답글 등록
	public int insertRecomment(SqlSession sqlSession, Comment comment);
}