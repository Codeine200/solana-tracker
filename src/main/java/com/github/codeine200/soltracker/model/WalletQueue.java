package com.github.codeine200.soltracker.model;

import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class WalletQueue {

    public final BlockingQueue<SolanaWallet> queue =
            new LinkedBlockingQueue<>(100_000);
}