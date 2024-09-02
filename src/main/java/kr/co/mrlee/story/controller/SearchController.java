package kr.co.mrlee.story.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.mrlee.story.dto.response.search.GetRelationWordListResponseDto;
import kr.co.mrlee.story.service.SearchService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {
	
	private final SearchService searchService;
	
//    @GetMapping("/popular-list")
//    public ResponseEntity<? super GetPopularListResponseDto> getPopularList() {
//        ResponseEntity<? super GetPopularListResponseDto> response = searchService.getPopularList();
//        return response;
//    }
//    
	
	@GetMapping("/{searchWord}/relation-list")
	public ResponseEntity<? super GetRelationWordListResponseDto> getRelationWordList(@PathVariable("searchWord") String searchWord) {
		ResponseEntity<? super GetRelationWordListResponseDto> response = searchService.getRelationWordList(searchWord);
		return response;
	}
	
}
