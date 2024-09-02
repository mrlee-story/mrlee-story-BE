package kr.co.mrlee.story.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kr.co.mrlee.story.dto.response.ResponseDto;
import kr.co.mrlee.story.dto.response.search.GetRelationWordListResponseDto;
import kr.co.mrlee.story.repository.SearchLogRepository;
import kr.co.mrlee.story.repository.resultset.GetRelationListResultSet;
import kr.co.mrlee.story.service.SearchService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
	
	private final SearchLogRepository searchLogRepository;
	
	@Override
	public ResponseEntity<? super GetRelationWordListResponseDto> getRelationWordList(String searchWord) {

        List<GetRelationListResultSet> resultSets = new ArrayList<>();

        try {
            resultSets = searchLogRepository.getRelationList(searchWord);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetRelationWordListResponseDto.success(resultSets);
	}

}
