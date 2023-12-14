package com.fiveguys.koguma.service.common;


import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.model.*;
import com.fiveguys.koguma.config.S3Config;
import com.fiveguys.koguma.data.dto.ImageDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.entity.Image;
import com.fiveguys.koguma.data.entity.ImageType;
import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.repository.common.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    private final S3Config s3Config;

    public void addImage(List<ImageDTO> imageDTOS) {
        List<Image> images = imageDTOS.stream().map(ImageDTO::toEntity).collect(Collectors.toList());

        imageRepository.saveAll(images);
    }


    public List<ImageDTO> listImage(ImageType imageType,Long targetId) throws Exception {

        List<ImageDTO> imageDTOS = new ArrayList<>();
        switch(imageType){
            case PRODUCT: {
                List<Image> images = imageRepository.findAllByProductId(targetId);
                imageDTOS =  images.stream().map((x) -> ImageDTO.fromEntity(x)).collect(Collectors.toList());
                break;
            }
            case CLUB:{
                List<Image> images = imageRepository.findAllByClubId(targetId);
                imageDTOS = images.stream().map((x) -> ImageDTO.fromEntity(x)).collect(Collectors.toList());
                break;
            }
            case POST:{
                List<Image> images = imageRepository.findAllByPostId(targetId);
                imageDTOS = images.stream().map((x) -> ImageDTO.fromEntity(x)).collect(Collectors.toList());
                break;
            }
//            case MESSAGE:{
//                List<Image> images = imageRepository.findAllByMessageId(targetId);
//                imageDTOS = images.stream().map((x) -> ImageDTO.fromEntity(x)).collect(Collectors.toList());
//                break;
//            }
            default:
                throw new Exception("요청 이미지 타입이 잘못 됐 습니다.");
        }
        return imageDTOS;
    }

    @Override
    public ImageDTO getImage(Long imageId) {
        return ImageDTO.fromEntity(imageRepository.findById(imageId).get());
    }

    public ImageDTO getRepImage(ImageType imageType, Long targetId) throws Exception {

        ImageDTO imageDTO = null;
        switch (imageType){
            case PRODUCT:{
                imageDTO = ImageDTO.fromEntity(imageRepository.findByProductIdAndRepImageFlagTrue(targetId));
                break;
            }
            case POST:{
                imageDTO = ImageDTO.fromEntity(imageRepository.findByPostIdAndRepImageFlagTrue(targetId));
                break;
            }
            case CLUB:{
                imageDTO = ImageDTO.fromEntity(imageRepository.findByClubIdAndRepImageFlagTrue(targetId));
                break;
            }
//            case MESSAGE:{
//                imageDTO = imageRepository.findByMESSAGEIdAndRepImageFlagTrue(targetId);
//                break;
//            }
            default:
                throw new Exception("요청 이미지 타입이 잘못 됐 습니다.");
        }
        return imageDTO;
    }

    @Transactional
    public void setRepImage(Long imageId) throws Exception {     // 이미지 수정때 대표이미지 변경가능


        Image newRepImage = imageRepository.findById(imageId).get();
        Image oldRepImage = null;

        switch (newRepImage.getImageType()){
            case PRODUCT:{
                oldRepImage = imageRepository.findByProductIdAndRepImageFlagTrue(newRepImage.getProduct().getId());
                break;
            }
            case POST:{
                oldRepImage = imageRepository.findByProductIdAndRepImageFlagTrue(newRepImage.getPost().getId());
                break;
            }
            case CLUB:{
                oldRepImage = imageRepository.findByProductIdAndRepImageFlagTrue(newRepImage.getClub().getId());
                break;
            }
//            case MESSAGE:{
//                oldRepImage = imageRepository.findByProductIdAndRepImageFlagTrue(newRepImage.getProduct().getId());
//                break;
//            }
            default:
                throw new Exception("요청 이미지 타입이 잘못 됐 습니다.");
        }
        oldRepImage.setRepImageFlag(false);
        newRepImage.setRepImageFlag(true);
    }

    public void updateProfilePicture(Member member,Long imageId) {
        member.setImageId(imageId);
    }


    public String tempFileUpload(MultipartFile multipartFile) throws IOException {
        // axios로 받은 file의 정보를 사용해 NCP의 Object Storage에 저장
        String originalFilename = multipartFile.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        s3Config.getS3().putObject(s3Config.getBucketName(), originalFilename, multipartFile.getInputStream(), metadata);
        setObjectACL(s3Config.getBucketName(), originalFilename);  // 저장하고 Access 권한까지 업데이트 해줘야함
        return s3Config.getS3().getUrl(s3Config.getBucketName(), originalFilename).toString();
    }
    public List<String> tempFileUpload(List<MultipartFile> multipartFileList) throws IOException {
        // axios로 받은 file의 정보를 사용해 NCP의 Object Storage에 저장
        List<String> fileUrlList = new ArrayList<>();
        multipartFileList.forEach(multipartFile -> {
            String originalFilename = multipartFile.getOriginalFilename();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());

            try {
                s3Config.getS3().putObject(s3Config.getBucketName(), originalFilename, multipartFile.getInputStream(), metadata);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            setObjectACL(s3Config.getBucketName(), originalFilename);  // 저장하고 Access 권한까지 업데이트 해줘야함
            fileUrlList.add(s3Config.getS3().getUrl(s3Config.getBucketName(), originalFilename).toString());
        });
        return fileUrlList;
    }
    public void setObjectACL(String bucketName,String objectName){
        try {
            // 현재 ACL의 정보를 가져온다
            AccessControlList accessControlList = s3Config.getS3().getObjectAcl(bucketName, objectName);

            // 읽기권한을 모든 유저에게 줌
            accessControlList.grantPermission(GroupGrantee.AllUsers, Permission.Read);

            //설정을 버킷에 적용시킨다.
            s3Config.getS3().setObjectAcl(bucketName, objectName, accessControlList);
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch(SdkClientException e) {
            e.printStackTrace();
        }
    }
}
