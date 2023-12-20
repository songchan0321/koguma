package com.fiveguys.koguma.service.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fiveguys.koguma.data.dto.ChatroomDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.ProductDTO;
import com.fiveguys.koguma.repository.chat.ChatroomRepository;
import com.fiveguys.koguma.service.common.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
    private final ChatroomRepository chatroomRepository;
    private final AlertService alertService;
    @Override
    public ChatroomDTO addChatroom(MemberDTO memberDTO, ProductDTO productDTO, boolean soloFlag) throws JsonProcessingException {
        LocalDateTime now = LocalDateTime.now();
        ChatroomDTO chatroomDTO = ChatroomDTO.builder()
                .buyerDTO(memberDTO)
                .productDTO(productDTO)
                .price(productDTO.getPrice())
                .sellerEnterDate(now)
                .buyerEnterDate(now)
                .activeFlag(true)
                .build();
        if(soloFlag) chatroomDTO.setSellerEnterDate(null);
        // 무조건 구매자가 먼저 (가격 제안 제외)
        ChatroomDTO newChatroomDTO = ChatroomDTO.formEntity(chatroomRepository.save(chatroomDTO.toEntity()));
        if(!soloFlag)
            alertService.addAlert(productDTO.getSellerDTO(), "채팅",  memberDTO.getNickname() + "님이 새로운 채팅을 시작했어요.", "/chat/get/" + newChatroomDTO.getId());
        return newChatroomDTO;
    }

    @Override
    public ChatroomDTO addChatroom(MemberDTO memberDTO, ProductDTO productDTO, Integer price, boolean soloFlag) throws Exception {
        ChatroomDTO chatroomDTO;
        if(this.existChatroom(memberDTO, productDTO)) {
            throw new Exception("기존 채팅방이 있습니다.");
//            chatroomDTO = ChatroomDTO.formEntity(chatroomRepository.findByBuyerAndProduct_Seller(memberDTO.toEntity(), productDTO.getSellerDTO().toEntity()).orElseThrow());
        } else {
            chatroomDTO = this.addChatroom(memberDTO, productDTO, soloFlag);
        }
        chatroomDTO.setPrice(price);
        alertService.addAlert(chatroomDTO.getBuyerDTO(), "가격 제안",  memberDTO.getNickname() + "님이 가격제안을 수락했어요.", "/chat/get/" + chatroomDTO.getId());
        return ChatroomDTO.formEntity(chatroomRepository.save(chatroomDTO.toEntity()));
    }
    @Override
    public boolean existChatroom(MemberDTO memberDTO, ProductDTO productDTO) {
        return chatroomRepository
                .findByBuyerAndProduct_Seller(
                        memberDTO.toEntity(),
                        productDTO.getSellerDTO().toEntity())
                .stream()
                .filter(chatroom -> chatroom.getActiveFlag())
                .count()
                > 0
                ? true
                : false;
    }

    @Override
    public ChatroomDTO getChatroomByProductAndMember(ProductDTO productDTO, MemberDTO buyerDTO) {
        return ChatroomDTO.formEntity(chatroomRepository.findByProductAndBuyer(productDTO.toEntity(), buyerDTO.toEntity()).orElseThrow());
    }

    @Override
    public ChatroomDTO updateChatroom(ChatroomDTO chatroomDTO) {
        return ChatroomDTO.formEntity(chatroomRepository.save(chatroomDTO.toEntity()));
    }

    @Override
    public ChatroomDTO getChatroom(Long id) throws Exception {
        ChatroomDTO chatroomDTO = ChatroomDTO.formEntity(chatroomRepository.findById(id).orElseThrow());
        if(!chatroomDTO.getActiveFlag()) {
            throw new Exception("채팅방이 없어요...");
        }
        return chatroomDTO;
    }


    @Override
    public ChatroomDTO enterChatroom(ChatroomDTO chatroomDTO, MemberDTO memberDTO){
        if(chatroomDTO.getSellerEnterDate() == null) {
            chatroomDTO.setSellerEnterDate(LocalDateTime.now());
            return ChatroomDTO.formEntity(chatroomRepository.save(chatroomDTO.toEntity()));
        }
        if(chatroomDTO.getBuyerEnterDate() == null) {
            chatroomDTO.setBuyerEnterDate(LocalDateTime.now());
            return ChatroomDTO.formEntity(chatroomRepository.save(chatroomDTO.toEntity()));
        }
//        if(this.isBuyer(chatroomDTO, memberDTO) && chatroomDTO.getSellerEnterDate() == null) {
//            chatroomDTO.setSellerEnterDate(LocalDateTime.now());
//            return ChatroomDTO.formEntity(chatroomRepository.save(chatroomDTO.toEntity()));
//        } else if(this.isSeller(chatroomDTO, memberDTO) && chatroomDTO.getBuyerEnterDate() == null) {
//            chatroomDTO.setBuyerEnterDate(LocalDateTime.now());
//            return ChatroomDTO.formEntity(chatroomRepository.save(chatroomDTO.toEntity()));
//        }
        return chatroomDTO;
    }
    @Override
    public List<ChatroomDTO> listChatroom(MemberDTO memberDTO) {
        List<ChatroomDTO> chatroomDTOList = chatroomRepository
                .findAllByProduct_Seller(memberDTO.toEntity())
                .stream()
                .filter(chatroom -> chatroom.getActiveFlag() && chatroom.getSellerEnterDate() != null)
                .map(ChatroomDTO::formEntity)
                .collect(Collectors.toList());
        chatroomDTOList.addAll(chatroomRepository
                .findAllByBuyer(memberDTO.toEntity())
                .stream()
                .filter(chatroom -> chatroom.getActiveFlag() && chatroom.getBuyerEnterDate() != null)
                .map(ChatroomDTO::formEntity)
                .collect(Collectors.toList())
        );
        return chatroomDTOList;
    }
    @Override
    public List<ChatroomDTO> listChatroom() {
        return chatroomRepository.findAll().stream().map(ChatroomDTO::formEntity).collect(Collectors.toList());
    }

    @Override
    public void exitChatroom(ChatroomDTO chatroomDTO, MemberDTO memberDTO) throws Exception {
        if(this.isBuyer(chatroomDTO, memberDTO)) {
            chatroomDTO.setBuyerEnterDate(null);
        } else if(this.isSeller(chatroomDTO, memberDTO)) {
            chatroomDTO.setSellerEnterDate(null);
        } else {
            throw new Exception("채팅방 나가는 권한이 없습니다...");
        }
        chatroomRepository.save(chatroomDTO.toEntity());
        if(chatroomDTO.getBuyerEnterDate() == null && chatroomDTO.getSellerEnterDate() == null) {
            this.deleteChatroom(chatroomDTO);
        }
    }

    @Override
    public void exitChatroomAllByBlockMember(MemberDTO soruceMemberDTO, MemberDTO targetMemberDTO) {
        this.listChatroom()
                .stream().filter(chatroomDTO ->
                        (chatroomDTO.getBuyerDTO().equals(targetMemberDTO) && chatroomDTO.getProductDTO().getSellerDTO().equals(soruceMemberDTO)))
                .map(chatroomDTO -> {
                    chatroomDTO.setSellerEnterDate(null);
                    return chatroomDTO;
                })
                .forEach(chatroomDTO -> chatroomRepository.save(chatroomDTO.toEntity()));
        this.listChatroom()
                .stream().filter(chatroomDTO ->
                        (chatroomDTO.getProductDTO().getSellerDTO().equals(targetMemberDTO) && chatroomDTO.getBuyerDTO().equals(soruceMemberDTO)))
                .map(chatroomDTO -> {
                    chatroomDTO.setBuyerEnterDate(null);
                    return chatroomDTO;
                })
                .forEach(chatRoomDTO -> chatroomRepository.save(chatRoomDTO.toEntity()));
    }

    @Override
    public void deleteChatroom(ChatroomDTO chatroomDTO) {
        chatroomDTO.setActiveFlag(false);
        chatroomRepository.save(chatroomDTO.toEntity());
    }

    private boolean isBuyer(ChatroomDTO chatroomDTO, MemberDTO memberDTO) {
        return Objects.equals(chatroomDTO.getBuyerDTO().getId(), memberDTO.getId());
    }

    private boolean isSeller(ChatroomDTO chatroomDTO, MemberDTO memberDTO) {
        return Objects.equals(chatroomDTO.getProductDTO().getSellerDTO().getId(), memberDTO.getId());
    }
}
