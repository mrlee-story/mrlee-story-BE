package kr.co.mrlee.story.dto.response.board;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.co.mrlee.story.common.ResponseStatus;
import kr.co.mrlee.story.dto.object.BoardListItem;
import kr.co.mrlee.story.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class GetSearchBoardListResponseDto extends ResponseDto {

    private List<BoardListItem> latestBoardList;

    private GetSearchBoardListResponseDto(List<BoardListItem> boardListViewEntitites) {
        super(ResponseStatus.SUCCESS);
        this.latestBoardList = boardListViewEntitites;
    }

    public static ResponseEntity<GetSearchBoardListResponseDto> success(List<BoardListItem> boardListViewEntities) {
        GetSearchBoardListResponseDto result = new GetSearchBoardListResponseDto(boardListViewEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    

}
