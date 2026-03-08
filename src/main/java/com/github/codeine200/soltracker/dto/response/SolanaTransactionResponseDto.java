package com.github.codeine200.soltracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolanaTransactionResponseDto {
    private String sender;
    private String receiver;
    private BigDecimal amount;
}
