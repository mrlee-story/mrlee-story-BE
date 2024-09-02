package kr.co.mrlee.story.dto.response.proxy;

import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import kr.co.mrlee.story.common.ResponseStatus;
import kr.co.mrlee.story.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class NotionProxyResponseDto extends ResponseDto {
	
	private JSONObject resultJsonText; 
	
	private NotionProxyResponseDto(JSONObject jsonObject) {
		super(ResponseStatus.SUCCESS);
		this.resultJsonText = jsonObject;
	}
	
	public static ResponseEntity<? super NotionProxyResponseDto> success(JSONObject jsonObject) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		NotionProxyResponseDto result = new NotionProxyResponseDto(jsonObject);
		ResponseEntity<NotionProxyResponseDto> response = new ResponseEntity<>(result, headers, HttpStatus.OK);
		return response;
	}
}
