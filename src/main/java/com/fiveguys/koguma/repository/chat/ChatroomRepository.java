package com.fiveguys.koguma.repository.chat;

import com.fiveguys.koguma.data.entity.Chatroom;
import com.fiveguys.koguma.data.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
    public List<Chatroom> findAllByProduct_Seller(Member member);
    public List<Chatroom> findAllByBuyer(Member member);
}
