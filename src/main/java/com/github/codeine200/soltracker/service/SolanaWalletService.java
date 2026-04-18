package com.github.codeine200.soltracker.service;

import com.github.codeine200.soltracker.model.SolanaWallet;
import org.p2p.solanaj.core.Account;
import org.springframework.stereotype.Service;

@Service
public class SolanaWalletService {

    public SolanaWallet createWallet() {
        Account account = new Account();
        System.out.println("Public key: " + account.getPublicKey().toBase58());
        byte[] secretKey = account.getSecretKey();
        System.out.println("Secret key length: " + secretKey.length);
        return new SolanaWallet(account.getPublicKey().toBase58(), secretKey);
    }

}
