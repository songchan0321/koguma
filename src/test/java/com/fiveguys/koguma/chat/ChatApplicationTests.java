package com.fiveguys.koguma.chat;

import com.fiveguys.koguma.data.dto.ChatroomDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.ProductDTO;
import com.fiveguys.koguma.service.chat.ChatService;
import com.fiveguys.koguma.service.member.MemberService;
import com.fiveguys.koguma.service.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@ActiveProfiles("local")
public class ChatApplicationTests {
    @Autowired private ProductService productService;
    @Autowired private MemberService memberService;
    @Autowired private ChatService chatService;
    @Test
    public void addChatroomTest() throws Exception {
        ProductDTO productDTO = productService.getProduct(1L);
        System.out.println(productDTO);
        MemberDTO memberDTO = memberService.getMember(2L);
        ChatroomDTO chatroomDTO = chatService.addChatroom(memberDTO, productDTO);
        assert chatroomDTO.getBuyerDTO().getId() == memberDTO.getId();
        assert chatroomDTO.getProductDTO().getId() == productDTO.getId();
        assert chatroomDTO.getPrice() == productDTO.getPrice();

        chatroomDTO = chatService.addChatroom(memberDTO, productDTO, 10000);
        assert chatroomDTO.getBuyerDTO().getId() == memberDTO.getId();
        assert chatroomDTO.getProductDTO().getId() == productDTO.getId();
        assert chatroomDTO.getPrice() == 10000;
        System.out.println(chatroomDTO);
    }

    @Test
    public void listChatroomTest() throws Exception {
        ProductDTO productDTO = productService.getProduct(1L);
        MemberDTO memberDTO = memberService.getMember(1L);
        if(!chatService.existChatroom(memberDTO, productDTO)) {
            chatService.addChatroom(memberDTO, productDTO);
        }
        List<ChatroomDTO> chatroomDTOList = chatService.listChatroom(memberDTO);
        assert chatroomDTOList.size() == 1;

        chatroomDTOList = chatService.listChatroom(productDTO.getSellerDTO());
        assert chatroomDTOList.size() == 1;

        memberDTO = memberService.getMember(2L);
        chatService.addChatroom(memberDTO, productDTO, 1000);

        chatroomDTOList = chatService.listChatroom(memberDTO);
        assert chatroomDTOList.size() == 1;
        ChatroomDTO chatroomDTO = chatroomDTOList.get(0);
        chatService.exitChatroom(chatroomDTO, memberDTO);

        chatroomDTOList = chatService.listChatroom(productDTO.getSellerDTO());
        assert chatroomDTOList.size() == 2;

        chatroomDTOList = chatService.listChatroom(memberDTO);
        assert chatroomDTOList.size() == 0;
        chatService.enterChatroom(chatroomDTO, memberDTO);

        chatroomDTOList = chatService.listChatroom(memberDTO);
        assert chatroomDTOList.size() == 1;
    }
}
