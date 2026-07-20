package com.milano.util;

import com.milano.dto.request.BookingRequestDTO;
import com.milano.dto.request.UserRequestDTO;
import com.milano.dto.response.PaymentOrderResponseDTO;
import com.milano.dto.response.PaymentResponseDTO;
import com.milano.entity.PAYMENT_METHOD;
import com.milano.entity.PAYMENT_STATUS;
import com.milano.entity.PaymentOrder;
import org.springframework.stereotype.Component;

@Component
public class PaymentOrderMapper {

    public PaymentOrder toPaymentOrder(UserRequestDTO userRequestDTO,
                                       BookingRequestDTO bookingRequestDTO,
                                       PAYMENT_METHOD paymentMethod){

        return PaymentOrder.builder()
                .amount(bookingRequestDTO.getTotalPrice())
                .paymentMethod(paymentMethod)
                .bookingId(bookingRequestDTO.getId())
                .salonId(bookingRequestDTO.getSalonId())
                .userId(userRequestDTO.getId())
                .status(PAYMENT_STATUS.PENDING)
                .build();

    }

    public PaymentOrderResponseDTO toPaymentOrderResponseDTO(PaymentOrder paymentOrder){
          return PaymentOrderResponseDTO.builder()
                  .id(paymentOrder.getId())
                  .amount(paymentOrder.getAmount())
                  .status(paymentOrder.getStatus())
                  .paymentMethod(paymentOrder.getPaymentMethod())
                  .paymentLinkId(paymentOrder.getPaymentLinkId())
                  .userId(paymentOrder.getUserId())
                  .bookingId(paymentOrder.getBookingId())
                  .salonId(paymentOrder.getSalonId())
                  .build();
    }
}
