package com.paymentservice.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="PAYMENT_TB")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Payment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;
    private String paymentStatus;
    private String transactionId;
    private int orderId;
    
    private double amount;
}
