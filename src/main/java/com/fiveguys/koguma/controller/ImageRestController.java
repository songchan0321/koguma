package com.fiveguys.koguma.controller;


import com.fiveguys.koguma.data.dto.ImageDTO;
import com.fiveguys.koguma.data.entity.ImageType;
import com.fiveguys.koguma.service.common.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//    @PostMapping("/new") //이미지 등록

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
