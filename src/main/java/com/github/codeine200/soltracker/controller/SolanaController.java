package com.github.codeine200.soltracker.controller;

import com.github.codeine200.soltracker.dto.response.SolanaWalletResponseDto;
import com.github.codeine200.soltracker.mapper.SolanaWalletMapper;
import com.github.codeine200.soltracker.service.SolanaWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/solana")
@RequiredArgsConstructor
public class SolanaController {

    private final SolanaWalletService walletService;
    private final SolanaWalletMapper mapper;

    @PostMapping("/wallets")
    public SolanaWalletResponseDto createWallet() {
        return mapper.toDto(walletService.createWallet());
    }
}
