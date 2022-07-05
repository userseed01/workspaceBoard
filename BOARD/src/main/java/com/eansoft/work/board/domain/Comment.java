package com.eansoft.work.board.domain;

import java.sql.Date;

public class Comment {
	private int commentNo;
	private int boardNo;
	private int parentCommentNo;
	private int commentOrder;
	private int commentDepth;
	private String emplId;
	private String commentContent;
	private Date commentWriteDate;
	private Date commentModifyDate;
	
	public Comment() {}

	public Comment(int commentNo, int boardNo, int parentCommentNo, int commentOrder, int commentDepth, String emplId,
			String commentContent, Date commentWriteDate, Date commentModifyDate) {
		super();
		this.commentNo = commentNo;
		this.boardNo = boardNo;
		this.parentCommentNo = parentCommentNo;
		this.commentOrder = commentOrder;
		this.commentDepth = commentDepth;
		this.emplId = emplId;
		this.commentContent = commentContent;
		this.commentWriteDate = commentWriteDate;
		this.commentModifyDate = commentModifyDate;
	}

	public int getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public int getParentCommentNo() {
		return parentCommentNo;
	}

	public void setParentCommentNo(int parentCommentNo) {
		this.parentCommentNo = parentCommentNo;
	}

	public int getCommentOrder() {
		return commentOrder;
	}

	public void setCommentOrder(int commentOrder) {
		this.commentOrder = commentOrder;
	}

	public int getCommentDepth() {
		return commentDepth;
	}

	public void setCommentDepth(int commentDepth) {
		this.commentDepth = commentDepth;
	}

	public String getEmplId() {
		return emplId;
	}

	public void setEmplId(String emplId) {
		this.emplId = emplId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public Date getCommentWriteDate() {
		return commentWriteDate;
	}

	public void setCommentWriteDate(Date commentWriteDate) {
		this.commentWriteDate = commentWriteDate;
	}

	public Date getCommentModifyDate() {
		return commentModifyDate;
	}

	public void setCommentModifyDate(Date commentModifyDate) {
		this.commentModifyDate = commentModifyDate;
	}

	@Override
	public String toString() {
		return "Comment [commentNo=" + commentNo + ", boardNo=" + boardNo + ", parentCommentNo=" + parentCommentNo
				+ ", commentOrder=" + commentOrder + ", commentDepth=" + commentDepth + ", emplId=" + emplId
				+ ", commentContent=" + commentContent + ", commentWriteDate=" + commentWriteDate
				+ ", commentModifyDate=" + commentModifyDate + "]";
	}
}