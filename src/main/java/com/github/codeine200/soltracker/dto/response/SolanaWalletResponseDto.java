package com.github.codeine200.soltracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SolanaWalletResponseDto {
    private final String address;
    private final String base64PrivateKey;
}
