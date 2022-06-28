package com.eansoft.work.board.service;

import java.util.List;

import com.eansoft.work.board.domain.Board;

public interface BoardService {

	// 게시판 메인 화면
	public List<Board> printAllBoard();

	// 게시글 상세조회 화면
	public Board printDetailBoard(Integer boardNo);

	// 게시글 작성
	public int boardWrite(Board board);

	// 게시글 수정
	public int boardModify(Board board);

}
