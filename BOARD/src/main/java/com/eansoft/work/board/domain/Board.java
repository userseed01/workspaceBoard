package com.eansoft.work.board.domain;

import java.sql.Date;
import java.util.List;


public class Board {

	private int boardNo;
	private String boardType;
	private String boardTitle;
	private String boardContent;
	// private List<BoardAttachedFile> bList; 첨부파일 나중에
	private String emplId;
	private Date boardDate;
	private int boardHits;
	
	public Board() {}

	public Board(int boardNo, String boardType, String boardTitle, String boardContent, String emplId, Date boardDate,
			int boardHits) {
		super();
		this.boardNo = boardNo;
		this.boardType = boardType;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.emplId = emplId;
		this.boardDate = boardDate;
		this.boardHits = boardHits;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public String getEmplId() {
		return emplId;
	}

	public void setEmplId(String emplId) {
		this.emplId = emplId;
	}

	public Date getBoardDate() {
		return boardDate;
	}

	public void setBoardDate(Date boardDate) {
		this.boardDate = boardDate;
	}

	public int getBoardHits() {
		return boardHits;
	}

	public void setBoardHits(int boardHits) {
		this.boardHits = boardHits;
	}

	@Override
	public String toString() {
		return "Board [boardNo=" + boardNo + ", boardType=" + boardType + ", boardTitle=" + boardTitle
				+ ", boardContent=" + boardContent + ", emplId=" + emplId + ", boardDate=" + boardDate + ", boardHits="
				+ boardHits + "]";
	}
}