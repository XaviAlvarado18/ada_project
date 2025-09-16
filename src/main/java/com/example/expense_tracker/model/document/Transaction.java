package com.example.expense_tracker.model.document;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "transactions")
public class Transaction {
    @Id
    private String id;
    private Long userId;
    private String type; // INCOME | EXPENSE
    private String category;
    private Double amount;
    private LocalDate date;
}
