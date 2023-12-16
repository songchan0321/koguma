package com.fiveguys.koguma.service.common;

import com.fiveguys.koguma.data.dto.ImageDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.ImageType;
import com.fiveguys.koguma.data.entity.Member;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    List<ImageDTO> addImage(List<ImageDTO> imageDTOS);
    List<ImageDTO> listImage(ImageType imageType, Long targetId) throws Exception;
    ImageDTO getImage(Long imageId);
    ImageDTO getRepImage(ImageType imageType, Long productId) throws Exception;

    void setRepImage(Long imageId) throws Exception;

    void updateProfilePicture(Member member, Long imageId);
    public String tempFileUpload(MultipartFile multipartFile) throws IOException;
    List<String> tempFileUpload(List<MultipartFile> multipartFileListDTO) throws IOException;
    public void setObjectACL(String bucketName,String objectName);
    List<ImageDTO> createImageDTOList(Object object, List<String> imageList, ImageType imageType);
    ImageDTO createImageDTO(Object object, String url,ImageType imageType,boolean repImageFlag);
}
