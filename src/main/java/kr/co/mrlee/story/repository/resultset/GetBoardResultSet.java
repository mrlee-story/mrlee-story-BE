package kr.co.mrlee.story.repository.resultset;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface GetBoardResultSet {
	int getBoardNumber();
	String getTitle();
	String getContent();
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	Date getRegdate();
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	Date getUpdatedate();
	
	int getWriterNumber();
	String getWriterEmail();
	String getWriterNickname();
	String getWriterProfileImage();
	int getAuthorizationLevel();
	
	int getViewCount();
	int getCommentCount();
	
	boolean getNotice();
	boolean getSecret();
}
