package com.fiveguys.koguma.controller;


import com.fiveguys.koguma.data.dto.ImageDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Image;
import com.fiveguys.koguma.data.entity.ImageType;
import com.fiveguys.koguma.service.common.ImageService;
import com.fiveguys.koguma.util.annotation.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Response;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageRestController {

    private final ImageService imageService;

    @GetMapping("/{imageId}") //이미지 가져오기
    public ResponseEntity<ImageDTO> getImage(@PathVariable Long imageId) {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.getImage(imageId));
    }

    @PostMapping("/new") //이미지 등록
    public ResponseEntity<List<String>> uploadFile(@RequestParam("file") List<MultipartFile> file) throws IOException {
        System.out.println("new 입장");
        List<String> fileList = imageService.tempFileUpload(file);
        return ResponseEntity.status(HttpStatus.OK).body(fileList);
    }
    @PostMapping("/add")
    public ResponseEntity<List<ImageDTO>> addImage(@RequestBody List<ImageDTO> imageDTO){
        List<ImageDTO> list = imageService.addImage(imageDTO);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/list") // 이미지 리스트 조회
    public ResponseEntity<List<ImageDTO>> listImage(Long targetId, ImageType imageType) throws Exception {
        List<ImageDTO> listImage = imageService.listImage(imageType, targetId);

        return ResponseEntity.status(HttpStatus.OK).body(listImage);
    }

    @PutMapping("{imageId}")
    public ResponseEntity<String> setRepImage(@PathVariable Long imageId) throws Exception {
        imageService.setRepImage(imageId);
        return ResponseEntity.status(HttpStatus.OK).body("대표이미지 변경 성공");
    }
}
