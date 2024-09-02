package kr.co.mrlee.story.dto.response.board;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.co.mrlee.story.common.ResponseStatus;
import kr.co.mrlee.story.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class PostBoardResponseDto extends ResponseDto {
	
	private int boardNumber;
	
	private PostBoardResponseDto(int boardNumber) {
		super(ResponseStatus.SUCCESS);
		this.boardNumber = boardNumber;
	}

    public static ResponseEntity<PostBoardResponseDto> success(int boardNumber) {
        PostBoardResponseDto result = new PostBoardResponseDto(boardNumber);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    
	public static ResponseEntity<? super PostBoardResponseDto> notExistUser() {
		ResponseDto result = new ResponseDto(ResponseStatus.NOT_EXISTED_USER);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

}
