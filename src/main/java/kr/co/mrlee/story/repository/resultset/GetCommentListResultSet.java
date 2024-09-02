package kr.co.mrlee.story.repository.resultset;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public interface GetCommentListResultSet {
	int getCommentNumber();
	
	int getWriterNumber();
	String getWriterEmail();
	String getWriterNickname();
	String getWriterProfileImage();
	int getWriterAuthorizationLevel();
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	Date getRegdate();
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	Date getUpdatedate();
	
	String getContent();
}
