package com.github.codeine200.soltracker.controller;

import com.github.codeine200.soltracker.dto.response.SlotResponseDto;
import com.github.codeine200.soltracker.dto.response.SolanaTransactionResponseDto;
import com.github.codeine200.soltracker.service.SolanaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
public class SlotController {

    private final SolanaService solanaService;

    @GetMapping("/last")
    public SlotResponseDto getLastSlot() {
        return solanaService.getLastSlot();
    }

    @GetMapping("/{slot}/transactions")
    public List<SolanaTransactionResponseDto> getTransactions(@PathVariable long slot) {
        return solanaService.getTransactionsBySlot(slot);
    }
}