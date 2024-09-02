package kr.co.mrlee.story.service.implement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.mrlee.story.repository.FileRepository;
import kr.co.mrlee.story.service.FileService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
	
	private final FileRepository fileRepo;
	private final ObjectMapper objectMapper;

	@Override
	public String upload(MultipartFile file) {
        if (file.isEmpty()) return null;

        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + extension;

        String url = fileRepo.save(file, saveFileName);
        return url;
	}

	@Override
	public Resource getImage(String fileName) {
        return fileRepo.get(fileName);
	}

	@Override
	public Resource getDefaultProfileImage(String fileName) {
		return fileRepo.getDefaultProfileImage(fileName);
	}

	@Override
	public List<String> getHistoryThumbnails() {
		return fileRepo.getHistoryThumbnails();
	}

	@Override
	public Resource getThumbnailImage(String fileName) {
		return fileRepo.getThumbnailImage(fileName);
	}

	@Override
	public List<List<Double>> getYColorArray() {
		File f = fileRepo.getThumbnailYColorArray();
		List<List<Double>> result = null;
		try {
			result = objectMapper.readValue(f, new TypeReference<List<List<Double>>>() {});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
		
//		return fileRepo.getYColorArray
	}

	@Override
	public void uploadThumbnailTmp() {
		File[] f = new File("D:\\폰 백업\\그린블루\\그린블루 생활-copy\\Thumbnail").listFiles();
		
		for (File img : f) {
			if (img.isDirectory()) continue;
			System.out.println(img.getName());
			if (!img.getName().contains(".png") && !img.getName().contains(".jpg"))continue;
			
			String originalFileName = img.getName();
	        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
	        String uuid = UUID.randomUUID().toString();
	        String saveFileName = "D:/ws/99_resources/mrlee-story/thumbnail/" + uuid + extension;
	
	        File output = new File(saveFileName);
	        try {
				Files.copy(img.toPath(), output.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
