package kr.co.mrlee.story.dto.response.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.co.mrlee.story.common.ResponseStatus;
import kr.co.mrlee.story.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class PostBoardGuestResponseDto extends ResponseDto {
	
	private int boardNumber;
	
	private PostBoardGuestResponseDto(int boardNumber) {
		super(ResponseStatus.SUCCESS);
		this.boardNumber = boardNumber;
	}

	public static ResponseEntity<? super PostBoardGuestResponseDto> success(int boardNumber) {
		PostBoardGuestResponseDto result = new PostBoardGuestResponseDto(boardNumber);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
