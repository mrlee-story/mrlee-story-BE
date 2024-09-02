package kr.co.mrlee.story.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface FileService {

	String upload(MultipartFile file);

	Resource getImage(String fileName);

	Resource getDefaultProfileImage(String fileName);

	void uploadThumbnailTmp();

	List<String> getHistoryThumbnails();

	Resource getThumbnailImage(String fileName);

	List<List<Double>> getYColorArray();
	
}
