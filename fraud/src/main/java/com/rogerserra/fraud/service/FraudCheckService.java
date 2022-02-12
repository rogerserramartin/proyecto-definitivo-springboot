package com.rogerserra.fraud.service;

import com.rogerserra.fraud.model.FraudCheckHistory;
import com.rogerserra.fraud.repository.FraudCHeckHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FraudCheckService {

    private  final FraudCHeckHistoryRepository fraudCHeckHistoryRepository;

    @Autowired
    public FraudCheckService(FraudCHeckHistoryRepository fraudCHeckHistoryRepository) {
        this.fraudCHeckHistoryRepository = fraudCHeckHistoryRepository;
    }

    public boolean isFraudulentCustomer(Integer customerId){
        fraudCHeckHistoryRepository.save(
                FraudCheckHistory.builder()
                        .customerId(customerId)
                        .isFraudster(false)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        return false;
    }




}
