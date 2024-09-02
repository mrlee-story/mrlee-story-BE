package kr.co.mrlee.story.service;

import org.springframework.http.ResponseEntity;

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

public interface BoardService {

	ResponseEntity<? super LatestBoardListResponseDto> getLatestBoardList(int cursor, int size, boolean notice);

	ResponseEntity<? super GetBoardResponseDto> getBoard(int boardNumber, String token);

	ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(int boardNumber);

	ResponseEntity<? super GetCommentListResponseDto> getCommentList(int boardNumber, int cursor, int size);

	ResponseEntity<? super GetSearchBoardListResponseDto> getSearchBoardList(String searchWord, String preSearchWord);

	ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email);

	ResponseEntity<? super PostBoardGuestResponseDto> postBoard(PostBoardGuestRequestDto dto);

	ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, int boardNumber, String token);
	
	ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(int boardNumber, String token);

	ResponseEntity<? super PatchBoardResponseDto> patchBoard(int boardNumber, PatchBoardRequestDto dto, String token);

	ResponseEntity<ResponseDto> patchComment(int commentNumber, PatchCommentRequestDto requestBody, String token);

	ResponseEntity<? super DeleteCommentResponseDto> deleteComment(int commentNumber, String token);


}
