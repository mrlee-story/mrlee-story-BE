package kr.co.mrlee.story.dto.response.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.co.mrlee.story.common.ResponseStatus;
import kr.co.mrlee.story.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class DeleteCommentResponseDto extends ResponseDto {
	
	private int commentCount;
	
	private DeleteCommentResponseDto(int commentCount) {
		super(ResponseStatus.SUCCESS);
		this.commentCount = commentCount;
	}

	public static ResponseEntity<? super DeleteCommentResponseDto> success(int commentCount) {
		DeleteCommentResponseDto result = new DeleteCommentResponseDto(commentCount);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	public static ResponseEntity<? super DeleteCommentResponseDto> noExistBoard() {
		ResponseDto result = new ResponseDto(ResponseStatus.NOT_EXISTED_BOARD);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

	public static ResponseEntity<? super DeleteCommentResponseDto> authorizationFail() {
		ResponseDto result = new ResponseDto(ResponseStatus.AUTHORIZATION_FAIL);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
	}
	
	public static ResponseEntity<? super DeleteCommentResponseDto> noExistUser() {
		ResponseDto result = new ResponseDto(ResponseStatus.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
	}
}
