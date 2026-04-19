package com.github.codeine200.soltracker.controller;

import com.github.codeine200.soltracker.dto.request.AddressRequestDto;
import com.github.codeine200.soltracker.dto.response.SolanaWalletResponseDto;
import com.github.codeine200.soltracker.mapper.SolanaWalletMapper;
import com.github.codeine200.soltracker.service.SolanaWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/wallets/by-address")
    public SolanaWalletResponseDto createWalletByAddress(@RequestBody AddressRequestDto request) {
        return mapper.toDto(walletService.createWalletByAddress(request.getAddress()));
    }

    @GetMapping("/wallets/{address}/history")
    public void getTransactionHistoryByAddress(@PathVariable String address) {
        walletService.getTransactionHistoryByAddress(address);
    }
}
