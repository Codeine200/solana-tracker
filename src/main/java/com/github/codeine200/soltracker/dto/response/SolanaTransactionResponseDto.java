package com.github.codeine200.soltracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolanaTransactionResponseDto {
    private String sender;
    private String receiver;
    private double amount;
}
