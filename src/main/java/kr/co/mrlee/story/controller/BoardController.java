package kr.co.mrlee.story.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.mrlee.story.dto.request.board.PatchBoardRequestDto;
import kr.co.mrlee.story.dto.request.board.PatchCommentRequestDto;
import kr.co.mrlee.story.dto.request.board.PostBoardGuestRequestDto;
import kr.co.mrlee.story.dto.request.board.PostBoardRequestDto;
import kr.co.mrlee.story.dto.request.board.PostCommentRequestDto;
import kr.co.mrlee.story.dto.response.ResponseDto;
import kr.co.mrlee.story.dto.response.board.DeleteBoardResponseDto;
import kr.co.mrlee.story.dto.response.board.DeleteCommentResponseDto;
import kr.co.mrlee.story.dto.response.board.GetBoardResponseDto;
import kr.co.mrlee.story.dto.response.board.GetCommentListResponseDto;
import kr.co.mrlee.story.dto.response.board.GetSearchBoardListResponseDto;
import kr.co.mrlee.story.dto.response.board.IncreaseViewCountResponseDto;
import kr.co.mrlee.story.dto.response.board.LatestBoardListResponseDto;
import kr.co.mrlee.story.dto.response.board.PatchBoardResponseDto;
import kr.co.mrlee.story.dto.response.board.PostBoardGuestResponseDto;
import kr.co.mrlee.story.dto.response.board.PostBoardResponseDto;
import kr.co.mrlee.story.dto.response.board.PostCommentResponseDto;
import kr.co.mrlee.story.provider.JwtProvider;
import kr.co.mrlee.story.service.BoardService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

	private final BoardService boardService;
	
	@GetMapping("/latest")
	public ResponseEntity<? super LatestBoardListResponseDto> getLatestBoardList(@RequestParam("cursor") int cursor, @RequestParam("size")int size, @RequestParam ("notice") boolean notice) {
		ResponseEntity<? super LatestBoardListResponseDto> response = boardService.getLatestBoardList(cursor, size, notice);
		return response;
	}
	
	@GetMapping("{boardNumber}")
	public ResponseEntity<? super GetBoardResponseDto> getBoard(@PathVariable("boardNumber") int boardNumber, @RequestHeader HttpHeaders headers) {
		String token = headers.get(JwtProvider.AUTHORIZATION_HEADER_KEY)==null? null : headers.get(JwtProvider.AUTHORIZATION_HEADER_KEY).get(0);
		
		ResponseEntity<? super GetBoardResponseDto> response = boardService.getBoard(boardNumber, token);
		return response;
	}
	
	@GetMapping("{boardNumber}/increase-view-count")
	public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(@PathVariable("boardNumber") int boardNumber) {
		ResponseEntity<? super IncreaseViewCountResponseDto> response = boardService.increaseViewCount(boardNumber);
		return response;
	}
	
    @GetMapping("/{boardNumber}/comment-list")
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(@PathVariable("boardNumber") int boardNumber, @RequestParam("cursor") int cursor, @RequestParam("size")int size) {
        ResponseEntity<? super GetCommentListResponseDto> response = boardService.getCommentList(boardNumber, cursor, size);
        return response;
    }
    
    @GetMapping(value={ "/search-list/{searchWord}", "/search-list/{searchWord}/{preSearchWord}" })
    public ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(
        @PathVariable("searchWord") String searchWord, @PathVariable(value="preSearchWord", required=false) String preSearchWord
    ) {
        ResponseEntity<? super GetSearchBoardListResponseDto> response = boardService.getSearchBoardList(searchWord, preSearchWord);
        return response;
    }
	
	@PostMapping("")
	public ResponseEntity<? super PostBoardResponseDto> postBoard(@RequestBody PostBoardRequestDto requestBody, @AuthenticationPrincipal String email) {
		ResponseEntity<? super PostBoardResponseDto> response = boardService.postBoard(requestBody, email);
		return response;
	}
	
	@PostMapping("/guest")
	public ResponseEntity<? super PostBoardGuestResponseDto> postBoard(@RequestBody PostBoardGuestRequestDto requestBody) {
		ResponseEntity<? super PostBoardGuestResponseDto> response = boardService.postBoard(requestBody);
		return response;
	}
	
    @PostMapping("/{boardNumber}/comment")
    public ResponseEntity<? super PostCommentResponseDto> postComment(@RequestBody PostCommentRequestDto dto, @PathVariable("boardNumber") int boardNumber, @RequestHeader HttpHeaders headers) {
    	String token = headers.get(JwtProvider.AUTHORIZATION_HEADER_KEY)==null? null : headers.get(JwtProvider.AUTHORIZATION_HEADER_KEY).get(0);
    	
        ResponseEntity<? super PostCommentResponseDto> response = boardService.postComment(dto, boardNumber, token);
        return response;
    }
	
	@PatchMapping("{boardNumber}")
	public ResponseEntity<? super PatchBoardResponseDto> patchBoard(@PathVariable("boardNumber") int boardNumber, @RequestBody PatchBoardRequestDto requestBody, @RequestHeader HttpHeaders headers) {
		String token = headers.get(JwtProvider.AUTHORIZATION_HEADER_KEY)==null? null : headers.get(JwtProvider.AUTHORIZATION_HEADER_KEY).get(0);
		
		ResponseEntity<? super PatchBoardResponseDto> response = boardService.patchBoard(boardNumber, requestBody, token);
		return response;
	}
	
	@PatchMapping("/comment/{commentNumber}")
	public ResponseEntity<ResponseDto> patchComment(@PathVariable("commentNumber") int commentNumber, @RequestBody PatchCommentRequestDto requestBody, @RequestHeader HttpHeaders headers) {
		String token = headers.get(JwtProvider.AUTHORIZATION_HEADER_KEY)==null? null : headers.get(JwtProvider.AUTHORIZATION_HEADER_KEY).get(0);
		ResponseEntity<ResponseDto> response = boardService.patchComment(commentNumber, requestBody, token);
		return response;
	}
	
	@DeleteMapping("{boardNumber}")
	public ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(@PathVariable("boardNumber") int boardNumber, @RequestHeader HttpHeaders headers) {
		String token = headers.get(JwtProvider.AUTHORIZATION_HEADER_KEY)==null? null : headers.get(JwtProvider.AUTHORIZATION_HEADER_KEY).get(0);
		
        ResponseEntity<? super DeleteBoardResponseDto> response = boardService.deleteBoard(boardNumber, token);
        return response;
    }
	
	@DeleteMapping("/comment/{commentNumber}")
	public ResponseEntity<? super DeleteCommentResponseDto> deleteComment(@PathVariable("commentNumber") int commentNumber, @RequestHeader HttpHeaders headers) {
		String token = headers.get(JwtProvider.AUTHORIZATION_HEADER_KEY)==null? null : headers.get(JwtProvider.AUTHORIZATION_HEADER_KEY).get(0);
		
		ResponseEntity<? super DeleteCommentResponseDto> response = boardService.deleteComment(commentNumber, token);
		return response;
	}
}
