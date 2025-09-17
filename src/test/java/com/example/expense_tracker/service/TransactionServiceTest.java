package com.example.expense_tracker.service;

import com.example.expense_tracker.model.document.Transaction;
import com.example.expense_tracker.model.entity.User;
import com.example.expense_tracker.repository.TransactionRepository;
import com.example.expense_tracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransactionService transactionService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
    }

    @Test
    void saveTransaction_ShouldSaveWithUserId() {
        Transaction tx = new Transaction();
        tx.setCategory("Food");
        tx.setType("EXPENSE");
        tx.setAmount(50.0);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(tx);

        Transaction saved = transactionService.saveTransaction(tx, "testuser");

        assertEquals("Food", saved.getCategory());
        verify(transactionRepository, times(1)).save(tx);
    }

    @Test
    void getTransactionsByUsername_ShouldReturnList() {
        Transaction tx = new Transaction();
        tx.setCategory("Salary");
        tx.setType("INCOME");
        tx.setAmount(1000.0);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(transactionRepository.findByUserId(1L)).thenReturn(List.of(tx));

        List<Transaction> result = transactionService.getTransactionsByUsername("testuser");

        assertEquals(1, result.size());
        assertEquals("Salary", result.get(0).getCategory());
    }

    @Test
    void getUserSummary_ShouldReturnCorrectSummary() {
        Transaction t1 = new Transaction();
        t1.setType("INCOME");
        t1.setCategory("Salary");
        t1.setAmount(2000.0);

        Transaction t2 = new Transaction();
        t2.setType("EXPENSE");
        t2.setCategory("Food");
        t2.setAmount(500.0);

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(transactionRepository.findByUserId(1L)).thenReturn(List.of(t1, t2));

        Map<String, Object> summary = transactionService.getUserSummary("testuser");

        assertEquals(2000.0, summary.get("totalIncome"));
        assertEquals(500.0, summary.get("totalExpense"));
        assertEquals(1500.0, summary.get("balance"));

        Map<String, Double> byCategory = (Map<String, Double>) summary.get("byCategory");
        assertTrue(byCategory.containsKey("Food"));
        assertTrue(byCategory.containsKey("Salary"));
    }
}
