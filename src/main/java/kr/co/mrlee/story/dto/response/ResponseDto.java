package kr.co.mrlee.story.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import kr.co.mrlee.story.common.ResponseStatus;
import lombok.Getter;

@Getter
public class ResponseDto {
	
	 private String code;
	 private String message;
	 
	 public ResponseDto(ResponseStatus resopnseStatus) {
		 this.code = resopnseStatus.getResponseCode();
		 this.message = resopnseStatus.getResponseMessage();
	 }
	 
	 public static ResponseEntity<ResponseDto> databaseError() {
	        ResponseDto responseBody = new ResponseDto(ResponseStatus.DATABASE_ERROR);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
	 }
	 
    public static ResponseEntity<ResponseDto> validationFalid() {
        ResponseDto responseBody = new ResponseDto(ResponseStatus.VALIDATION_FAILED);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
