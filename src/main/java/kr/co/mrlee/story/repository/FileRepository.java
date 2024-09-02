package kr.co.mrlee.story.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class FileRepository {
	

	
	@Value("${file.path}")
	private String filePath;
	@Value("${file.url}")
	private String fileUrl;

	public String save(MultipartFile file, String saveFileName) {
        try {
            String savePath = filePath + saveFileName;
            file.transferTo(new File(savePath));
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }
        
        String url = fileUrl + saveFileName;
        
        return url;
	}

	public Resource get(String fileName) {
        Resource resource = null;
        try {
            resource = new UrlResource("file:" + filePath + fileName);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }
        return resource;
	}
	
	public String getRandomProfileImageUrl() {
		File[] files = new File(filePath+"profile/default/").listFiles();

        if (files == null || files.length == 0) {
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(files.length); // 0부터 files.length-1까지의 랜덤 인덱스 생성
        
        String fileName = files[randomIndex].getName();
        
        String url = fileUrl + "profile/default/" + fileName;
        return url;
	}

	public Resource getDefaultProfileImage(String fileName) {
        Resource resource = null;
        try {
            resource = new UrlResource("file:" + filePath + "profile/default/" + fileName);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }
        return resource;
	}

	public void deleteByUrl(List<String> deleteFileList) {
		for (String url : deleteFileList) {
			String fileName = url.replace(fileUrl, "");
			Path fullPath = Paths.get(filePath + fileName);
			
			try {
				Files.deleteIfExists(fullPath);
			} catch (IOException e) {
				log.warn(e.getMessage());
				System.out.println(e.getMessage());
			}
		}
		
	}

	public List<String> getHistoryThumbnails() {
		File[] imageList = new File(filePath+"thumbnail").listFiles();
		
		List<String> result = new ArrayList<>();
		
		for (File file : imageList) {
			if (file.isDirectory()) continue;
			if (!file.getName().contains(".jpg") && !file.getName().contains(".png")) continue;
			
			String url = fileUrl+"thumbnail/"+file.getName();
			result.add(url);
		}
		return result;
	}

	public Resource getThumbnailImage(String fileName) {
		Resource resource = null;
        try {
            resource = new UrlResource("file:" + filePath + "thumbnail/" + fileName);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }
        return resource;
	}

	public File getThumbnailYColorArray() {
		File f = new File(filePath+"thumbnail/Y_COLOR_ARRAY.json");
		return f;
	}

}
