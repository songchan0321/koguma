package com.fiveguys.koguma.controller;

import com.fiveguys.koguma.data.dto.ChatroomDTO;
import com.fiveguys.koguma.data.dto.MemberDTO;
import com.fiveguys.koguma.service.chat.ChatService;
import com.fiveguys.koguma.util.annotation.CurrentMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/chat")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ChatRestController {
    private final ChatService chatService;
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<ChatroomDTO>> listChatRoom (
            @CurrentMember MemberDTO memberDTO
    ) {
        return ResponseEntity.ok().body(chatService.listChatroom(memberDTO));
    }

    @RequestMapping(value = "/get/{roomId}", method = RequestMethod.GET)
    public ResponseEntity<ChatroomDTO> getChatRoom(
            @CurrentMember MemberDTO memberDTO,
            @PathVariable Long roomId
    ) throws Exception {
        return ResponseEntity.ok().body(chatService.getChatroom(roomId));
    }
}
