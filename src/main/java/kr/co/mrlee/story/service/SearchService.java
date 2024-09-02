package kr.co.mrlee.story.service;

import org.springframework.http.ResponseEntity;

import kr.co.mrlee.story.dto.response.search.GetRelationWordListResponseDto;

public interface SearchService {

	ResponseEntity<? super GetRelationWordListResponseDto> getRelationWordList(String searchWord);

}
