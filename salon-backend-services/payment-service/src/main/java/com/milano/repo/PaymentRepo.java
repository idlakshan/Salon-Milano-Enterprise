package com.milano.repo;

import com.milano.entity.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepo extends JpaRepository<PaymentOrder, UUID> {

    PaymentOrder findByPaymentLinkIdIs(String paymentId);

}
