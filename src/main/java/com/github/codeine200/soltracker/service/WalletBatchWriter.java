package com.github.codeine200.soltracker.service;


import com.github.codeine200.soltracker.model.SolanaWallet;
import com.github.codeine200.soltracker.model.WalletQueue;
import com.github.codeine200.soltracker.repository.SolanaWalletRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class WalletBatchWriter {

    private final WalletQueue walletQueue;
    private final SolanaWalletRepository repository;
    private final ExecutorService writerPool = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void start() {
        writerPool.submit(this::run);
    }

    private void run() {
        List<SolanaWallet> batch = new ArrayList<>(1000);

        while (true) {
            try {
                SolanaWallet wallet = walletQueue.queue.take();
                batch.add(wallet);

                if (batch.size() >= 1000) {
                    repository.saveBatch(batch);
                    batch.clear();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}