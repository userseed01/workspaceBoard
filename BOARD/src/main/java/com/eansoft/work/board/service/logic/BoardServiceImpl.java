package com.eansoft.work.board.service.logic;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eansoft.work.board.domain.Board;
import com.eansoft.work.board.domain.Comment;
import com.eansoft.work.board.domain.File;
import com.eansoft.work.board.domain.PageCount;
import com.eansoft.work.board.service.BoardService;
import com.eansoft.work.board.store.BoardStore;
import com.eansoft.work.common.Search;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardStore bStore;
	
	@Autowired
	private SqlSession sqlSession;
	
	// 게시판 메인 화면
	@Override
	public List<Board> printAllBoard(PageCount pc) {
		List<Board> bList = bStore.selectAllMain(sqlSession, pc);
		return bList;
	}

	// 게시판 메인 페이징
	@Override
	public int getListCount() {
		int result= bStore.selectListCount(sqlSession);
		return result;
	}
	
	// 게시글 상세조회 화면
	@Override
	public Board printDetailBoard(Integer boardNo) {
		Board board = bStore.selectDetailBoard(sqlSession, boardNo);
		return board;
	}

	// 게시글 상세조회 화면 조회수 증가
	@Override
	public int viewCount(Integer boardNo) {
		int result = bStore.updateViewCount(sqlSession, boardNo);
		return result;
	}
	
	// 게시글 작성
	@Override
	public int boardWrite(Board board) {
		int result = bStore.insertBoard(sqlSession, board);
		return result;
	}

	// 게시글 작성 시 첨부파일 업로드
	@Override
	public int registerBoardFile(File file) {
		int result = bStore.insertFile(sqlSession, file);
		return result;
	}
	
	// 게시글 수정
	@Override
	public int boardModify(Board board) {
		int result = bStore.updateBoard(sqlSession, board);
		return result;
	}

	// 게시글 삭제 화면
	@Override
	public int boardDelete(int boardNo) {
		int result = bStore.deleteBoard(sqlSession, boardNo);
		return result;
	}
	
	// 게시판 검색 화면
	@Override
	public List<Board> printSearchBoard(Search search, PageCount pc) {
		List<Board> bList = bStore.selectSearchBoard(sqlSession, search, pc);
		return bList;
	}

	// 게시판 검색 페이징
	@Override
	public int boardSearchListCount(Search search) {
		int result = bStore.selectSearchCount(sqlSession, search);
		return result;
	}

	// 게시글 상세조회 시 댓글 조회 화면
	@Override
	public List<Comment> printAllComment(int boardNo) {
		List<Comment> cList = bStore.selectCommentBoard(sqlSession, boardNo);
		return cList;
	}

	// 게시글 상세조회 시 댓글 등록
	@Override
	public int addComment(Comment comment) {
		int result = bStore.insertComment(sqlSession, comment);
		return result;
	}

	// 게시글 상세 조회 시 댓글 수정
	@Override
	public int modifyComment(Comment comment) {
		int result = bStore.updateComment(sqlSession, comment);
		return result;
	}

	// 게시글 상세 조회 시 댓글 삭제
	@Override
	public int removeComment(int commentNo) {
		int result = bStore.deleteComment(sqlSession, commentNo);
		return result;
	}

	// 게시글 상세 조회 시 댓글의 답글 등록
	@Override
	public int recommentAdd(Comment comment) {
		int result = bStore.insertRecomment(sqlSession, comment);
		return result;
	}
}