package kr.co.mrlee.story.dto.response.board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.co.mrlee.story.common.ResponseStatus;
import kr.co.mrlee.story.dto.object.CommentListItem;
import kr.co.mrlee.story.dto.response.ResponseDto;
import kr.co.mrlee.story.repository.resultset.GetCommentListResultSet;
import lombok.Getter;

@Getter
public class GetCommentListResponseDto extends ResponseDto {
	
	private List<CommentListItem> commentList;
	private int currentCursor;
	
	private GetCommentListResponseDto(List<GetCommentListResultSet> resultSets) {
		super(ResponseStatus.SUCCESS);
		this.commentList = CommentListItem.copyList(resultSets);
		if (resultSets != null && resultSets.size()>0) {
			this.currentCursor = resultSets.get(resultSets.size()-1).getCommentNumber();
		}
	}

	public static ResponseEntity<? super GetCommentListResponseDto> success(List<GetCommentListResultSet> resultSets) {
		GetCommentListResponseDto result = new GetCommentListResponseDto(resultSets);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	public static ResponseEntity<? super GetCommentListResponseDto> noExistBoard() {
		ResponseDto result = new ResponseDto(ResponseStatus.NOT_EXISTED_BOARD);
		return ResponseEntity.status(HttpStatus.valueOf(ResponseStatus.NOT_EXISTED_BOARD.ordinal())).body(result);
	}
	
}
