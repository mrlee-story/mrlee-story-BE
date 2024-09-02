package kr.co.mrlee.story.dto.response.board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.co.mrlee.story.common.ResponseStatus;
import kr.co.mrlee.story.dto.object.BoardListItem;
import kr.co.mrlee.story.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class LatestBoardListResponseDto extends ResponseDto {
	
	private List<BoardListItem> latestBoardList;
	private int currentCursor;
	
	private LatestBoardListResponseDto(List<BoardListItem> latestBoardList) {
		super(ResponseStatus.SUCCESS);
		if (latestBoardList!=null && latestBoardList.size()>0) {
			this.latestBoardList = latestBoardList;
			this.currentCursor = latestBoardList.get(latestBoardList.size()-1).getBoardNumber();
		} else {
			this.latestBoardList = new ArrayList<>();
		}
	}


	public static ResponseEntity<? super LatestBoardListResponseDto> success(List<BoardListItem> latestBoardList) {
		LatestBoardListResponseDto result = new LatestBoardListResponseDto(latestBoardList);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}
