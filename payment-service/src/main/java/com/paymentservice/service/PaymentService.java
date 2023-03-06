package com.paymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymentservice.entity.Payment;
import com.paymentservice.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;


@Service
@Slf4j
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Payment doPayment(Payment payment) throws JsonProcessingException {
        String s = UUID.randomUUID().toString();
        payment.setTransactionId(s);
        payment.setPaymentStatus(paymentProcesing());
        payment.setTransactionId(UUID.randomUUID().toString());
        log.info("Payment-Service Request: {}", new ObjectMapper().writeValueAsString(payment));
        return paymentRepository.save(payment);


    }

    //    api should be 3rd party payment gateway(paypal,paytm);
    public String paymentProcesing() {
        return new Random().nextBoolean() ? "success" : "false";
    }

    public Payment findPaymentHistoryByOrderId(int orderId) throws JsonProcessingException {

        Payment payment = paymentRepository.findByOrderId(orderId);
        log.info("paymentService findPaymentHistoryByOrderId : {}", new ObjectMapper().writeValueAsString(payment));
        return payment;
    }
}
