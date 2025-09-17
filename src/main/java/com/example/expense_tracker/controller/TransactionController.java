package com.example.expense_tracker.controller;

import com.example.expense_tracker.model.document.Transaction;
import com.example.expense_tracker.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // 🔹 Crear transacción (toma el userId del usuario autenticado)
    @PostMapping
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction tx) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // el "sub" del JWT (usualmente el username)

        return ResponseEntity.ok(transactionService.saveTransaction(tx, username));
    }

    // 🔹 Listar transacciones del usuario autenticado
    @GetMapping
    public ResponseEntity<List<Transaction>> getUserTransactions() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return ResponseEntity.ok(transactionService.getTransactionsByUsername(username));
    }

    // 🔹 Resumen del usuario autenticado
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getUserSummary() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        return ResponseEntity.ok(transactionService.getUserSummary(username));
    }

}
