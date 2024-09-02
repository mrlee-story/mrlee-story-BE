package kr.co.mrlee.story.dto.response.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.co.mrlee.story.common.ResponseStatus;
import kr.co.mrlee.story.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class PostCommentResponseDto extends ResponseDto {
	
	private int commentCount;
	
	private PostCommentResponseDto(int commentCount) {
		super(ResponseStatus.SUCCESS);
		this.commentCount = commentCount;
	}

	public static ResponseEntity<? super PostCommentResponseDto> success(int commentCount) {
		PostCommentResponseDto result = new PostCommentResponseDto(commentCount);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	public static ResponseEntity<? super PostCommentResponseDto> noExistBoard() {
		ResponseDto result = new ResponseDto(ResponseStatus.NOT_EXISTED_BOARD);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

	public static ResponseEntity<? super PostCommentResponseDto> authorizationFail() {
		ResponseDto result = new ResponseDto(ResponseStatus.AUTHORIZATION_FAIL);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
	}
	
	public static ResponseEntity<? super PostCommentResponseDto> noExistUser() {
		ResponseDto result = new ResponseDto(ResponseStatus.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
	}
}
