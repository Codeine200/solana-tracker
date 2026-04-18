package com.github.codeine200.soltracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SolanaWallet {
    private final String address;
    private final byte[] privateKey;
}
