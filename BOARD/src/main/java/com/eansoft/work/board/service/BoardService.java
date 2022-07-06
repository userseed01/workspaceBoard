package com.eansoft.work.board.service;

import java.util.List;

import com.eansoft.work.board.domain.Board;
import com.eansoft.work.board.domain.Comment;
import com.eansoft.work.board.domain.File;
import com.eansoft.work.board.domain.PageCount;
import com.eansoft.work.common.Search;

public interface BoardService {

	// 게시판 메인 화면
	public List<Board> printAllBoard(PageCount pc);

	// 게시판 메인 페이징
	public int getListCount();
	
	// 게시글 상세조회 화면
	public Board printDetailBoard(Integer boardNo);

	// 게시글 상세조회 화면 조회수 증가
	public int viewCount(Integer boardNo);
	
	// 게시글 작성
	public int boardWrite(Board board);

	// 게시글 작성 시 첨부파일 업로드
	public int registerBoardFile(File file);

	// 게시글 수정
	public int boardModify(Board board);

	// 게시글 삭제 화면
	public int boardDelete(int boardNo);

	// 게시판 검색 화면
	public List<Board> printSearchBoard(Search search, PageCount pc);

	// 게시판 검색 페이징
	public int boardSearchListCount(Search search);

	// 게시글 상세조회 시 댓글 조회 화면
	public List<Comment> printAllComment(int boardNo);

	// 게시글 상세조회 시 댓글 등록
	public int addComment(Comment comment);

	// 게시글 상세 조회 시 댓글 수정
	public int modifyComment(Comment comment);

	// 게시글 상세 조회 시 댓글 삭제
	public int removeComment(int commentNo);
}