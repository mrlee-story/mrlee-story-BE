package kr.co.mrlee.story.service.implement;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import kr.co.mrlee.story.dto.response.proxy.NotionProxyResponseDto;
import kr.co.mrlee.story.service.ProxyService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProxyServiceImpl implements ProxyService {
	
	@Value("${notion.secret-key}")
	private String notionApiKey;
	@Value("${notion.version}")
	private String notionVersion;
	
	@Value("${notion.request-url-database.format}")
	private String notionDatabaseUrlFormat;
	@Value("${notion.request-url-page.format}")
	private String notionPagesUrlFormat;
	@Value("${notion.request-url-blocks.format}")
	private String notionBlocksUrlFormat;
	@Value("${notion.request-url-page-splitbee.format}")
	private String notionPageSplitbeeUrlFormat;
	
	@Value("${notion.database-id.project}")
	private String notionProjectDbId;
	@Value("${notion.database-id.work}")
	private String notionWorkDbId;
	
	private static final RestTemplate REST_TEMPLATE;
	private static final JSONParser jsonParser = new JSONParser();
	
	static {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(5000);
		factory.setReadTimeout(5000);
		REST_TEMPLATE = new RestTemplate(factory);
	}
	

	@Override
	public ResponseEntity<? super NotionProxyResponseDto> getNotionProjectData() {
//		JSONArray result = new JSONArray();
		JSONObject result = new JSONObject();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(notionApiKey);
			headers.add("Notion-Version", notionVersion);
			String url = String.format(notionDatabaseUrlFormat, notionProjectDbId);
			HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(null, headers);
			ResponseEntity<String> response = REST_TEMPLATE.postForEntity(url, requestEntity, String.class);
			
			if (response!=null && StringUtils.hasText(response.getBody())) {
				result = (JSONObject)jsonParser.parse(response.getBody());
			}
			// TODO
//			JSONArray originResult = (JSONArray)((JSONObject)jsonParser.parse(response.getBody())).get("results");
//			for (Object item : originResult) {
//				JSONObject obj = (JSONObject)item;
//				obj.remove("created_time");
//				obj.remove("parent");
//				obj.remove("in_trash");
//				obj.remove("created_by");
//				obj.remove("cover");
//				obj.remove("archived");
//				obj.remove("last_edited_time");
//				obj.remove("last_edited_by");
//				obj.remove("object");
//				result.add(obj);
//			}
		} catch (Exception e) {
			e.printStackTrace();
			return NotionProxyResponseDto.databaseError();
		}
		return NotionProxyResponseDto.success(result);
	}
	
	@Override
	public ResponseEntity<? super NotionProxyResponseDto> getNotionWorkData() {
		JSONObject result = new JSONObject();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(notionApiKey);
			headers.add("Notion-Version", notionVersion);
			String url = String.format(notionDatabaseUrlFormat, notionWorkDbId);
			HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(null, headers);
			ResponseEntity<String> response = REST_TEMPLATE.postForEntity(url, requestEntity, String.class);
			// TODO
			if (response!=null && StringUtils.hasText(response.getBody())) {
				result = (JSONObject)jsonParser.parse(response.getBody());
			}
			// TODO
//			JSONArray originResult = (JSONArray)((JSONObject)jsonParser.parse(response.getBody())).get("results");
//			for (Object item : originResult) {
//				JSONObject obj = (JSONObject)item;
//				obj.remove("created_time");
//				obj.remove("parent");
//				obj.remove("in_trash");
//				obj.remove("created_by");
//				obj.remove("cover");
//				obj.remove("archived");
//				obj.remove("last_edited_time");
//				obj.remove("last_edited_by");
//				obj.remove("object");
//				result.add(obj);
//			}
		} catch (Exception e) {
			e.printStackTrace();
			return NotionProxyResponseDto.databaseError();
		}
		return NotionProxyResponseDto.success(result);
	}


	@Override
	public ResponseEntity<? super NotionProxyResponseDto> getNotionPageData(String pageID) {
		JSONObject result = new JSONObject();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(notionApiKey);
			headers.add("Notion-Version", notionVersion);
			String pagesUrl = String.format(notionPagesUrlFormat, pageID);
			HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(null, headers);
			ResponseEntity<String> responsePage = REST_TEMPLATE.exchange(pagesUrl, HttpMethod.GET, requestEntity, String.class);
			// TODO
			JSONObject pageData = (JSONObject)jsonParser.parse(responsePage.getBody());
			
			String blocksUrl = String.format(notionBlocksUrlFormat, pageID);
			ResponseEntity<String> responseBlock = REST_TEMPLATE.exchange(blocksUrl, HttpMethod.GET, requestEntity, String.class);
			JSONArray blockData = (JSONArray)((JSONObject)jsonParser.parse(responseBlock.getBody())).get("results");
			result.put("page", pageData);
			result.put("block", blockData);
			// TODO
//			JSONArray originResult = (JSONArray)((JSONObject)jsonParser.parse(response.getBody())).get("results");
//			for (Object item : originResult) {
//				JSONObject obj = (JSONObject)item;
//				obj.remove("created_time");
//				obj.remove("parent");
//				obj.remove("in_trash");
//				obj.remove("created_by");
//				obj.remove("cover");
//				obj.remove("archived");
//				obj.remove("last_edited_time");
//				obj.remove("last_edited_by");
//				obj.remove("object");
//				result.add(obj);
//			}
		} catch (Exception e) {
			e.printStackTrace();
			return NotionProxyResponseDto.databaseError();
		}
		return NotionProxyResponseDto.success(result);
	}


	@Override
	public ResponseEntity<? super NotionProxyResponseDto> getNotionPageSplitbee(String pageID) {
		JSONObject result = null;
		try {
			String pageUrl = String.format(notionPageSplitbeeUrlFormat, pageID);
			ResponseEntity<String> responsePage = REST_TEMPLATE.getForEntity(pageUrl, String.class);
			result = (JSONObject)jsonParser.parse(responsePage.getBody());
		} catch (Exception e) {
			e.printStackTrace();
			return NotionProxyResponseDto.databaseError();
		}
		
		return NotionProxyResponseDto.success(result);
	}


}
