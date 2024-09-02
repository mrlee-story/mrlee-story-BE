package kr.co.mrlee.story.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.mrlee.story.dto.request.board.PatchBoardRequestDto;
import kr.co.mrlee.story.dto.request.board.PostBoardGuestRequestDto;
import kr.co.mrlee.story.dto.request.board.PostBoardRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "board")
@Table(name = "board")
public class BoardEntity {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int boardNumber;
	private String title;
	private String content;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date regdate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date updatedate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date deletedate;
	
	private boolean secret;
	private boolean notice;
	
	private int commentCount;
	private int viewCount;
	
	private Integer guestNumber;
	private Integer memberNumber;
	
	public BoardEntity(PostBoardRequestDto dto, MemberEntity member) {
		this.title = dto.getTitle();
		this.content = dto.getContent();
		this.regdate = new Date();
		this.secret = dto.isSecret();
		this.notice = dto.isNotice();
		this.commentCount = 0;
		this.viewCount = 0;
		this.memberNumber = member.getMemberNumber();
	}

	public BoardEntity(PostBoardGuestRequestDto dto, GuestEntity guest) {
		this.title = dto.getTitle();
		this.content = dto.getContent();
		this.regdate = new Date();
		this.secret = dto.isSecret();
		this.notice = false;
		this.commentCount = 0;
		this.viewCount = 0;
		this.guestNumber = guest.getGuestNumber();
	}
	
	public BoardEntity delete() {
		try {
			this.deletedate = new Date();
			return this;
		} catch (Exception e) {
			return null;
		}
	}

	public BoardEntity patch(PatchBoardRequestDto dto) {
		//	patch 날짜
		this.updatedate = new Date();
		
		//	patch 내용
		this.title = dto.getTitle();
		this.content = dto.getContent();
		this.secret = dto.isSecret();
		this.notice = dto.isNotice();
		
		return this;
	}
	
	public BoardEntity increaseViewCount() {
		this.viewCount++;
		return this;
	}

	public BoardEntity increaseCommentCount() {
		this.commentCount++;
		return this;
	}
	
	public BoardEntity decreaseCommentCount() {
		this.commentCount--;
		return this;
	}
}
