package com.rogerserra.fraud.repository;

import com.rogerserra.fraud.model.FraudCheckHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FraudCHeckHistoryRepository
        extends JpaRepository<FraudCheckHistory, Integer> {
}
