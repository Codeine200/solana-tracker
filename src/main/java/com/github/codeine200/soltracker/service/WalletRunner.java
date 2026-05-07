package com.github.codeine200.soltracker.service;

import com.github.codeine200.soltracker.model.SolanaWallet;
import com.github.codeine200.soltracker.model.WalletQueue;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class WalletRunner {

    private final SolanaWalletService solanaWalletService;
    private final WalletQueue queue;

    private final ExecutorService pool =
            Executors.newFixedThreadPool(10);

    @PostConstruct
    public void start() {
        for (int i = 0; i < 10; i++) {
            pool.submit(this::generateLoop);
        }
    }

    private void generateLoop() {
        while (true) {
            try {
                SolanaWallet wallet = solanaWalletService.createWallet();
                queue.queue.put(wallet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}