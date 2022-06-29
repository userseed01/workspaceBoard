package com.eansoft.work.common;

import com.eansoft.work.board.domain.PageCount;

public class Pagination {
	public static PageCount getPageCount(int currentPage, int totalCount) {
		PageCount pc = null;

		// 페이지 설정해주는 곳
		
		int boardLimit = 10; // 한 페이지 당 게시글 갯수
		int naviLimit = 10; // 한 페이지 당 pageNavi 수
		int maxPage; // 페이지의 마지막 번호
		int startNavi; // pagenavi 시작 값
		int endNavi; // pagenavi 끝 값
		
		// 23/5 = 4.8 + 0.9(항상 올림을 하기위해) = 5.7 -> int로 강제 형변환
		maxPage = (int) ((double) totalCount / boardLimit + 0.9);
		// 1 <- 1 2 3 4 5, 6 <- 6 7 8 9 10, 11 -> 11~15(11에 5더하고 1빼면 11)
		// 1~5 까지면 스타트가 항상 1이어야 함
		startNavi = (((int) ((double) currentPage / naviLimit + 0.9)) - 1) * naviLimit + 1;
		endNavi = startNavi + naviLimit - 1;
		// 오류방지 코드
		if (maxPage < endNavi) {
			endNavi = maxPage;
		}
		pc = new PageCount(currentPage, boardLimit, naviLimit, startNavi, endNavi, totalCount, maxPage);
		return pc;
	}
}