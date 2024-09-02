package kr.co.mrlee.story.service;

import org.springframework.http.ResponseEntity;

import kr.co.mrlee.story.dto.response.proxy.NotionProxyResponseDto;

public interface ProxyService {

	ResponseEntity<? super NotionProxyResponseDto> getNotionProjectData();

	ResponseEntity<? super NotionProxyResponseDto> getNotionWorkData();

	ResponseEntity<? super NotionProxyResponseDto> getNotionPageData(String pageID);

	ResponseEntity<? super NotionProxyResponseDto> getNotionPageSplitbee(String pageID);

}
