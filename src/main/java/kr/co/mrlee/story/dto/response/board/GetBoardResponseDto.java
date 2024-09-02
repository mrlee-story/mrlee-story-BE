package kr.co.mrlee.story.dto.response.board;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.co.mrlee.story.common.ResponseStatus;
import kr.co.mrlee.story.dto.object.BoardListItem;
import kr.co.mrlee.story.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class GetBoardResponseDto extends ResponseDto {
	
	private BoardListItem boardListItem;
	private int contentAuthLevel;

	private GetBoardResponseDto(BoardListItem board) {
		super(ResponseStatus.SUCCESS);
		this.boardListItem = board;
		this.contentAuthLevel = board.getWriterAuthorizationLevel();
	}
	
	private GetBoardResponseDto(ResponseStatus status, int contentAuthLevel) {
		super(status);
		this.contentAuthLevel = contentAuthLevel;
	}

	public static ResponseEntity<? super GetBoardResponseDto> success(BoardListItem board) {
		GetBoardResponseDto result = new GetBoardResponseDto(board);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	public static ResponseEntity<? super GetBoardResponseDto> noExistBoard() {
		ResponseDto result = new ResponseDto(ResponseStatus.NOT_EXISTED_BOARD);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

	public static ResponseEntity<? super GetBoardResponseDto> authorizationFail(int authLevel) {
		GetBoardResponseDto result = new GetBoardResponseDto(ResponseStatus.AUTHORIZATION_FAIL, authLevel);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
	}
}
