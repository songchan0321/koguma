package com.fiveguys.koguma.repository.payment;

import com.fiveguys.koguma.data.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentHistoryRepository extends JpaRepository<Long, PaymentHistory> {
}
