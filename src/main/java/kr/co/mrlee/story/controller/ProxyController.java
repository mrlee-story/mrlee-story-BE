package kr.co.mrlee.story.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.mrlee.story.dto.response.proxy.NotionProxyResponseDto;
import kr.co.mrlee.story.dto.response.proxy.NotionProxyResponseTestDto;
import kr.co.mrlee.story.service.ProxyService;
import lombok.RequiredArgsConstructor;
import notion.api.v1.NotionClient;
import notion.api.v1.model.blocks.Blocks;
import notion.api.v1.model.pages.Page;
import notion.api.v1.request.blocks.RetrieveBlockChildrenRequest;
import notion.api.v1.request.pages.RetrievePageRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/proxy")
public class ProxyController {
	
	private final ProxyService proxyService;
	
	@GetMapping("/notion/databases/project")
	public ResponseEntity<? super NotionProxyResponseDto> getNotionProject() {
		ResponseEntity<? super NotionProxyResponseDto> response = proxyService.getNotionProjectData();
        return response;
	}
	
	@GetMapping("/notion/databases/work")
	public ResponseEntity<? super NotionProxyResponseDto> getNotionWork() {
		ResponseEntity<? super NotionProxyResponseDto> response = proxyService.getNotionWorkData();
		return response;
	}
	
	@GetMapping("/notion/pages/{pageId}")
	public ResponseEntity<? super NotionProxyResponseDto> getNotionPages(@PathVariable("pageId") String pageID) {
		ResponseEntity<? super NotionProxyResponseDto> response = proxyService.getNotionPageData(pageID);
        return response;
	}
	
	@GetMapping("/notion/page/splitbee/{pageId}")
	public ResponseEntity<? super NotionProxyResponseDto> getNotionPageSplitbee(@PathVariable("pageId") String pageID) {
		ResponseEntity<? super NotionProxyResponseDto> response = proxyService.getNotionPageSplitbee(pageID);
        return response;
	}
	
	@GetMapping("/notion/test/{pageId}")
	public ResponseEntity<? super NotionProxyResponseTestDto> testNotion(@PathVariable("pageId") String pageID) {
		NotionClient client = new NotionClient("secret_pbhCg8EwRwSA7HmuMyXD0kHBKs6LIukTSQiQbsxnmfN");
		
		RetrievePageRequest req = new RetrievePageRequest(pageID);
		Page page = client.retrievePage(req);
		
		RetrieveBlockChildrenRequest reqChild = new RetrieveBlockChildrenRequest(pageID);
		Blocks blockMap = client.retrieveBlockChildren(reqChild);
		
		Map<String, Object> data = new HashMap<>();
		data.put("block", blockMap)
		;
		data.put("page", page);
//		Assert.assertNotNull(result);
//		System.out.println(result);
		client.close();
		
		
		ResponseEntity<? super NotionProxyResponseTestDto> response = NotionProxyResponseTestDto.success(data);
		
		return response;
	}

}
