package kr.co.mrlee.story.dto.response.proxy;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import kr.co.mrlee.story.common.ResponseStatus;
import kr.co.mrlee.story.dto.response.ResponseDto;
import lombok.Getter;

@Getter
public class NotionProxyResponseTestDto extends ResponseDto {
	
	private Object resultJsonText; 
	
	private NotionProxyResponseTestDto(Object jsonObject) {
		super(ResponseStatus.SUCCESS);
		this.resultJsonText = jsonObject;
	}
	
	public static ResponseEntity<? super NotionProxyResponseTestDto> success(Object jsonObject) {
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
		NotionProxyResponseTestDto result = new NotionProxyResponseTestDto(jsonObject);
		ResponseEntity<NotionProxyResponseTestDto> response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
}
