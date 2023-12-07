package com.fiveguys.koguma.repository.payment;

import com.fiveguys.koguma.data.entity.Member;
import com.fiveguys.koguma.data.entity.PaymentHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, UUID>{
//    Page<PaymentHistory> findAllByMember(Member member, Specification<PaymentHistory> spec, Pageable pageable);
    Page<PaymentHistory> findAll(Specification<PaymentHistory> spec, Pageable pageable);
}
