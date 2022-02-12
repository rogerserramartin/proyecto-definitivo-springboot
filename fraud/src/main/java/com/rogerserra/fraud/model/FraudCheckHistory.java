package com.rogerserra.fraud.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class FraudCheckHistory {

    @Id

    @SequenceGenerator(
            name = "fraud_id_sequence",
            sequenceName = "fraud_idsequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "fraud_id_sequence"
    )


    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fraud_id")
    private Integer id;
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "is_fraudster")
    private Boolean isFraudster;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
