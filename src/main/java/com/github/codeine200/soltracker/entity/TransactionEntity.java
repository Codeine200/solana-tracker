package com.github.codeine200.soltracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tx_seq")
    @SequenceGenerator(
            name = "tx_seq",
            sequenceName = "transactions_seq",
            allocationSize = 1
    )
    private Long id;

    private String fromAddress;
    private String toAddress;
    private BigDecimal amount;
}