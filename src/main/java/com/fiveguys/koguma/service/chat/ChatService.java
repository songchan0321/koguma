package com.fiveguys.koguma.service.chat;

import com.fiveguys.koguma.data.dto.ChatroomDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.data.dto.ProductDTO;

import java.util.List;

public interface ChatService {
    public ChatroomDTO addChatroom(MemberDTO memberDTO, ProductDTO productDTO);
    public ChatroomDTO addChatroom(MemberDTO memberDTO, ProductDTO productDTO, Integer price);
    public boolean existChatroom(MemberDTO memberDTO, ProductDTO productDTO);
    public ChatroomDTO getChatroom(Long id) throws Exception;
    public List<ChatroomDTO> listChatroom(MemberDTO memberDTO);
    public void enterChatroom(ChatroomDTO chatroomDTO, MemberDTO memberDTO) throws Exception;
    public void exitChatroom(ChatroomDTO chatroomDTO, MemberDTO memberDTO) throws Exception;
    public void deleteChatroom(ChatroomDTO chatroomDTO);
}
