package com.fiveguys.koguma.service.common;


import com.fiveguys.koguma.data.dto.ImageDTO;
import com.fiveguys.koguma.repository.common.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {


    private final ImageRepository imageRepository;

    @Override
    public void addImage() {

    }

    @Override
    public List<ImageDTO> listImage() {
        return null;
    }
}
