package com.fiveguys.koguma.service.common;


import com.fiveguys.koguma.data.dto.ImageDTO;
import com.fiveguys.koguma.repository.common.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    private ImageRepository imageRepository;

    @Override
    public void addImage() {

    }

    @Override
    public List<ImageDTO> listImage() {
        return null;
    }
}
