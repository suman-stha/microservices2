package com.orderservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderservice.common.Payment;
import com.orderservice.common.TransactionRequest;
import com.orderservice.common.TransactionResponse;
import com.orderservice.entity.Order;
import com.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RestTemplate template;


    public TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException {
        String response = "";
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());
        //rest call

        log.info("Order-service Request: " + new ObjectMapper().writeValueAsString(request));
        Payment paymentResponse = template.postForObject("http://Payment-Service/payment/doPayment", payment, Payment.class);
        response = paymentResponse.getPaymentStatus().equals("success") ? "payment processing successfully" : "There is a failure in payment api, order added to cart";
        log.info("Order Service getting Response from payment-service: " + new ObjectMapper().writeValueAsString(response));

        orderRepository.save(order);
        return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(), response);

    }

}
