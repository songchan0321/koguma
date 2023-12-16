package com.fiveguys.koguma.service.chat;

import com.fiveguys.koguma.data.dto.ChatroomDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.ProductDTO;
import com.fiveguys.koguma.repository.chat.ChatroomRepository;
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
    @Override
    public ChatroomDTO addChatroom(MemberDTO memberDTO, ProductDTO productDTO) {
        LocalDateTime now = LocalDateTime.now();
        ChatroomDTO chatroomDTO = ChatroomDTO.builder()
                .buyerDTO(memberDTO)
                .productDTO(productDTO)
                .price(productDTO.getPrice())
                .sellerEnterDate(now)
                .buyerEnterDate(now)
                .activeFlag(true)
                .build();
        return ChatroomDTO.formEntity(chatroomRepository.save(chatroomDTO.toEntity()));
    }

    @Override
    public ChatroomDTO addChatroom(MemberDTO memberDTO, ProductDTO productDTO, Integer price) throws Exception {
        ChatroomDTO chatroomDTO;
        if(memberDTO.getId().equals(productDTO.getSellerDTO().getId())) throw new Exception("자신의 상품에 채팅할 수 없습니다.");
        if(this.existChatroom(memberDTO, productDTO)) {
            chatroomDTO = ChatroomDTO.formEntity(chatroomRepository.findByBuyerAndProduct_Seller(memberDTO.toEntity(), productDTO.getSellerDTO().toEntity()).orElseThrow());
        } else {
            chatroomDTO = this.addChatroom(memberDTO, productDTO);
        }
        chatroomDTO.setPrice(price);
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
        if(this.isBuyer(chatroomDTO, memberDTO) && chatroomDTO.getSellerEnterDate() == null) {
            chatroomDTO.setSellerEnterDate(LocalDateTime.now());
            return ChatroomDTO.formEntity(chatroomRepository.save(chatroomDTO.toEntity()));
        } else if(this.isSeller(chatroomDTO, memberDTO) && chatroomDTO.getBuyerEnterDate() == null) {
            chatroomDTO.setBuyerEnterDate(LocalDateTime.now());
            return ChatroomDTO.formEntity(chatroomRepository.save(chatroomDTO.toEntity()));
        }
        return null;
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
