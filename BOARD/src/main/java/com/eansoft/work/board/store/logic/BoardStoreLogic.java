package com.eansoft.work.board.store.logic;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.eansoft.work.board.domain.Board;
import com.eansoft.work.board.domain.PageCount;
import com.eansoft.work.board.store.BoardStore;

@Repository
public class BoardStoreLogic implements BoardStore{

	// select는 List & One 둘중에 하나로 적어줘야함
	
	// 게시판 메인 화면
	@Override
	public List<Board> selectAllMain(SqlSession sqlSession, PageCount pc) {
		/**
		 * RowBounds는 쿼리문을 변경하지 않고도 페이징을 처리할 수 있게 해주는 클래스 RowBounds의 동작은 offset값과
		 * limit값을 이용해서 동작함 offset값은 변하는 값이고 limit값은 고정값 limit값이 한 페이지당 보여주고 싶은 게시물의
		 * 갯수이고 offset은 건너뛰어야 할 값임 ex) limit 10, offset 0, 1~10 offset 10, 11~20
		 * offset값은 currentPage에 의해서 변경됨
		 */
		int limit = pc.getBoardLimit();
		int currentPage = pc.getCurrentPage();
		int offset = (currentPage - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit); // 커런트페이지는 오프셋과 함께 넘어감
		List<Board> bList = sqlSession.selectList("BoardMapper.selectAllBoard", pc, rowBounds);
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

	// 게시글 삭제 화면
	@Override
	public int deleteBoard(SqlSession sqlSession, int boardNo) {
		int result = sqlSession.delete("BoardMapper.deleteBoard", boardNo);
		return result;
	}

	// 게시판 메인 페이징
	@Override
	public int selectListCount(SqlSession sqlSession) {
		int result = sqlSession.selectOne("BoardMapper.selectPage"); // 갯수 하나만 받는거라 One
		return result;
	}
}