package kr.co.mrlee.story.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kr.co.mrlee.story.dto.request.board.PatchCommentRequestDto;
import kr.co.mrlee.story.dto.request.board.PostCommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "comment")
@Table(name = "comment")
public class CommentEntity {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int commentNumber;
	private int boardNumber;
	private String content;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date regdate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date updatedate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date deletedate;
	
	private Integer guestNumber;
	private Integer memberNumber;
	

	public CommentEntity(int boardNumber, PostCommentRequestDto dto, MemberEntity member) {
		this.boardNumber = boardNumber;
		this.content = dto.getContent();
		this.regdate = new Date();
		this.memberNumber = member.getMemberNumber();
		this.guestNumber = null;
	}


	public CommentEntity(int boardNumber, PostCommentRequestDto dto, GuestEntity guest) {
		this.boardNumber = boardNumber;
		this.content = dto.getContent();
		this.regdate = new Date();
		this.guestNumber = guest.getGuestNumber();
		this.memberNumber = null;
	}


	public CommentEntity patch(PatchCommentRequestDto requestBody) {
		this.updatedate =new Date();
		this.content = requestBody.getContent();
		return this;
	}


	public CommentEntity delete() {
		this.deletedate = new Date();
		return this;
	}
}
