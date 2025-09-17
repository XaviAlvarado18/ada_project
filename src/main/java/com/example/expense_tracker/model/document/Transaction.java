package com.example.expense_tracker.model.document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
