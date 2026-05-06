package com.github.codeine200.soltracker.service;

import com.github.codeine200.soltracker.model.SolanaWallet;
import com.github.codeine200.soltracker.repository.SolanaWalletRepository;
import lombok.AllArgsConstructor;
import org.p2p.solanaj.core.Account;
import org.p2p.solanaj.core.PublicKey;
import org.p2p.solanaj.rpc.RpcClient;
import org.p2p.solanaj.rpc.RpcException;
import org.p2p.solanaj.rpc.types.SignatureInformation;
import org.p2p.solanaj.rpc.types.config.Commitment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Service
@AllArgsConstructor
public class SolanaWalletService {

    private final RpcClient client;
    private final SolanaWalletRepository repository;

    public SolanaWallet createWallet() {
        Account account = new Account();
        byte[] secretKey = account.getSecretKey();
        return new SolanaWallet(
                account.getPublicKey().toBase58(),
                secretKey
        );
    }

    public void getTransactionHistoryByAddress(String address) {

        PublicKey publicKey = new PublicKey(address);
        List<SignatureInformation> signatures = null;
        try {
            signatures = client.getApi().getSignaturesForAddress(publicKey,2, Commitment.FINALIZED);
        } catch (RpcException e) {
            throw new RuntimeException(e);
        }

        for (SignatureInformation sig : signatures) {

            try {
                var transaction = client.getApi().getTransaction(sig.getSignature());
                System.out.println(transaction);
            } catch (RpcException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public SolanaWallet createWalletByAddress(String address) {

        String prefix = getPrefix(address, 3);
        String suffix = getSuffix(address, 3);

        AtomicLong counter = new AtomicLong(0);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Generated accounts: " + counter.get());
        }, 1, 1, TimeUnit.SECONDS);

        try {
            while (true) {
                Account account = new Account();
                counter.incrementAndGet();

                String newAddress = account.getPublicKey().toBase58();
                byte[] secretKey = account.getSecretKey();
                System.out.println(newAddress.length() + " : " + secretKey.length);

                if (newAddress.startsWith(prefix) && newAddress.endsWith(suffix)) {
                    System.out.println("FOUND: " + newAddress);

       //             byte[] secretKey = account.getSecretKey();

                    scheduler.shutdown();

                    return new SolanaWallet(newAddress, secretKey);
                }
            }
        } finally {
            scheduler.shutdownNow();
        }
    }

    private String getPrefix(String address, int length) {
        if (address == null || address.length() < length) {
            throw new IllegalArgumentException("Address too short");
        }
        return address.substring(0, length);
    }

    private String getSuffix(String address, int length) {
        if (address == null || address.length() < length) {
            throw new IllegalArgumentException("Address too short");
        }
        return address.substring(address.length() - length);
    }

}
