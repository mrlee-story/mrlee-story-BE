package kr.co.mrlee.story.dto.response.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.co.mrlee.story.common.ResponseStatus;
import kr.co.mrlee.story.dto.response.ResponseDto;
import kr.co.mrlee.story.repository.resultset.GetRelationListResultSet;
import lombok.Getter;

@Getter
public class GetRelationWordListResponseDto extends ResponseDto {
	
	private List<String> relationWordList;
	
	private GetRelationWordListResponseDto(List<GetRelationListResultSet> resultSets) {
		super(ResponseStatus.SUCCESS);

        List<String> relativeWordList = new ArrayList<>();
        for (GetRelationListResultSet item : resultSets) {
            String relativeWord = item.getSearchWord();
            relativeWordList.add(relativeWord);
        }
        this.relationWordList = relativeWordList;
	}	
	
    public static ResponseEntity<? super GetRelationWordListResponseDto> success(List<GetRelationListResultSet> resultSets) {
        GetRelationWordListResponseDto result = new GetRelationWordListResponseDto(resultSets);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
