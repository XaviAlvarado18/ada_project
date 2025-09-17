package com.example.expense_tracker.repository;

import com.example.expense_tracker.model.document.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByUserId(Long userId);
}
