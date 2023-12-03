package com.fiveguys.koguma.repository.chat;

import com.fiveguys.koguma.data.entity.Chatroom;
import com.fiveguys.koguma.data.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
    public List<Chatroom> findAllByProduct_Seller(Member member);
    public List<Chatroom> findAllByBuyer(Member member);
    public Optional<Chatroom> findByBuyerAndProduct_Seller(Member buyer, Member seller);
    public Optional<Chatroom> findByProduct_SellerAndBuyer(Member seller, Member buyer);
}
