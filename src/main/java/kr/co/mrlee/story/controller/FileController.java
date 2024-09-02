package kr.co.mrlee.story.controller;


import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.co.mrlee.story.service.FileService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        String url = fileService.upload(file);
        return url;
    }

    @GetMapping(value="{fileName}", produces={MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public Resource getImage(@PathVariable("fileName") String fileName) {
        Resource resource = fileService.getImage(fileName);
        return resource;
    }
    
    @GetMapping(value="/profile/default/{fileName}", produces={MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public Resource getDefaultProfileImage(@PathVariable("fileName") String fileName) {
    	Resource resource = fileService.getDefaultProfileImage(fileName);
    	return resource;
    }
    
    // 썸네일 리스트
    @GetMapping(value="/history/thumbnails")
    public List<String> getHistoryThumbnails() {
    	List<String> result = fileService.getHistoryThumbnails();
    	return result;
    }
    
    // 개별 썸네일 이미지
    @GetMapping(value="/thumbnail/{fileName}", produces={MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public Resource getThumbnailImage(@PathVariable("fileName") String fileName) {
    	Resource resource = fileService.getThumbnailImage(fileName);
    	return resource;
    }
    
    // 흑백 y값
    @GetMapping(value="/thumbnail/meta/y")
    public List<List<Double>> getYColorArray() {
    	List<List<Double>> yColorArray = fileService.getYColorArray();
    	return yColorArray;
    }
    
    @GetMapping("/test/testtesttest/TODO/delete/upload/thumbnail")
    public String uploadThumbnailTmp() {
    	fileService.uploadThumbnailTmp();
    	return "SUCCESS";
    }
}
