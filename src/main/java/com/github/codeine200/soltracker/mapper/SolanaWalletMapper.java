package com.github.codeine200.soltracker.mapper;

import com.github.codeine200.soltracker.dto.response.SolanaWalletResponseDto;
import com.github.codeine200.soltracker.model.SolanaWallet;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class SolanaWalletMapper {

    public SolanaWalletResponseDto toDto(SolanaWallet wallet) {
        return new SolanaWalletResponseDto(
                wallet.getAddress(),
                Base64.getEncoder().encodeToString(wallet.getPrivateKey())
        );
    }
}
