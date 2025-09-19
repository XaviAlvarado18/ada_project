package com.example.expense_tracker.service;

import com.example.expense_tracker.model.document.Transaction;
import com.example.expense_tracker.model.entity.User;
import com.example.expense_tracker.repository.TransactionRepository;
import com.example.expense_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Transaction saveTransaction(Transaction tx, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        tx.setUserId(user.getId());
        return transactionRepository.save(tx);
    }

    public List<Transaction> getTransactionsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return transactionRepository.findByUserId(user.getId());
    }

    public Map<String, Object> getUserSummary(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return buildSummary(transactionRepository.findByUserId(user.getId()));
    }


    // Method for build summay
    private Map<String, Object> buildSummary(List<Transaction> transactions) {
        double totalIncome = transactions.stream()
                .filter(tx -> "INCOME".equalsIgnoreCase(tx.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalExpense = transactions.stream()
                .filter(tx -> "EXPENSE".equalsIgnoreCase(tx.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        Map<String, Double> byCategory = transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getCategory,
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        Map<String, Object> summary = new HashMap<>();
        summary.put("totalIncome", totalIncome);
        summary.put("totalExpense", totalExpense);
        summary.put("balance", totalIncome - totalExpense);
        summary.put("byCategory", byCategory);

        return summary;
    }
}

