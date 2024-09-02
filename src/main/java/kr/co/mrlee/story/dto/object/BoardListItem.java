package kr.co.mrlee.story.dto.object;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import kr.co.mrlee.story.entity.BoardEntity;
import kr.co.mrlee.story.entity.BoardImageEntity;
import kr.co.mrlee.story.repository.resultset.GetBoardResultSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardListItem {
	
	private int boardNumber;
	private String title;
	private String content;
	private List<String> boardImageList;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date regdate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date updatedate;
	
	private int writerNumber;
	private String writerEmail;
	private String writerNickname;
	private String writerProfileImage;
	private int writerAuthorizationLevel;
	
	private int viewCount;
	private int commentCount;
	
	private boolean notice;
	private boolean secret;
	

	public BoardListItem(GetBoardResultSet rs, List<BoardImageEntity> imageEntities) {
		this.boardNumber = rs.getBoardNumber();
		this.title = rs.getTitle();
		this.content = rs.getContent();
		
		this.regdate = rs.getRegdate();
		this.updatedate = rs.getUpdatedate();
		
		this.writerNumber = rs.getWriterNumber();
		this.writerEmail = rs.getWriterEmail();
		if (writerEmail!=null) {
			writerEmail = writerEmail.replaceAll("^(.{3})[^@]*(@.+)$", "$1****$2");
		}
		this.writerNickname = rs.getWriterNickname();
		this.writerProfileImage = rs.getWriterProfileImage();
		this.writerAuthorizationLevel = rs.getAuthorizationLevel();
		
		this.viewCount = rs.getViewCount();
		this.commentCount = rs.getCommentCount();
		
		this.notice = rs.getNotice();
		this.secret = rs.getSecret();
		
		this.boardImageList = new ArrayList<>();
		
		for (BoardImageEntity boardImageEntity : imageEntities) {
			String url = boardImageEntity.getImageUrl();
			this.boardImageList.add(url);
		}
	}


	public BoardListItem(GetBoardResultSet rs) {
		this.boardNumber = rs.getBoardNumber();
		this.title = rs.getTitle();
		this.content = rs.getContent();
		
		this.regdate = rs.getRegdate();
		this.updatedate = rs.getUpdatedate();
		
		this.writerNumber = rs.getWriterNumber();
		this.writerEmail = rs.getWriterEmail();
		if (writerEmail!=null) {
			writerEmail = writerEmail.replaceAll("^(.{3})[^@]*(@.+)$", "$1****$2");
		}
		this.writerNickname = rs.getWriterNickname();
		this.writerProfileImage = rs.getWriterProfileImage();
		this.writerAuthorizationLevel = rs.getAuthorizationLevel();
		
		this.viewCount = rs.getViewCount();
		this.commentCount = rs.getCommentCount();
		
		this.notice = rs.getNotice();
		this.secret = rs.getSecret();
		
		this.boardImageList = new ArrayList<>();
	}


	public void setImage(List<BoardImageEntity> imageEntities) {
		if (this.boardImageList==null) {
			this.boardImageList = new ArrayList<>();
		}
		for (BoardImageEntity boardImageEntity : imageEntities) {
			String url = boardImageEntity.getImageUrl();
			this.boardImageList.add(url);
		}
	}

}
