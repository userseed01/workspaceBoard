package com.eansoft.work.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public class SaveAttachedFile {
	
	// 파일 1개씩만 올릴 수 있어서 controller에서 for문 돌려 여러개 올릴 수 있게 해주기

	// 첨부파일 저장
	public static HashMap<String, String> saveFile(MultipartFile file, HttpServletRequest request) {
		String filePath = "";
		HashMap<String, String> fileMap = new HashMap<String, String>(); // map -> 리스트처럼 담아서 보냄
		// 파일 경로설정(상대경로)
		String root = request.getSession().getServletContext().getRealPath("resources");
		// 저장 폴더 선택
		String savePath = root + "\\uploadFiles";
		// 없으면 생성
		File folder = new File(savePath);
		if (!folder.exists())
			folder.mkdir();
		// 날짜 포맷변경
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		// 업로드한 파일명(원래 파일명)
		String originalFileName = file.getOriginalFilename();
		// 파일 확장자명(jpg, png 등...)
		String extensionName = originalFileName.substring(originalFileName.lastIndexOf("."));
		// 파일명 변경
		String renameFileName = sdf.format(new Date(System.currentTimeMillis())) + extensionName;
		filePath = folder + "\\" + renameFileName;
		// 두가지 값을 map에 저장하여 리턴하기
		fileMap.put("filePath", filePath); // ""키값 , value값 -> 담아서 보냄(넣은)
		fileMap.put("fileName", originalFileName);
		fileMap.put("fileRename", renameFileName);
		// 파일저장
		try {
			file.transferTo(new File(filePath));// 경로가 새로 지정될때마다 new를 해줘야한다.(파일저장)
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 파일경로 리턴
		return fileMap;
	}

	// 첨부파일 삭제
	public static void deleteFile(String filePath, HttpServletRequest request) {
		File deleteFile = new File(filePath);
		if (deleteFile.exists()) {
			// 파일이 존재하면 파일 삭제
			deleteFile.delete();
		}
	}
}