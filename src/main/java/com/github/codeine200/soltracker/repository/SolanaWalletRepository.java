package com.github.codeine200.soltracker.repository;

import com.github.codeine200.soltracker.model.SolanaWallet;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Base64;

@Repository
@AllArgsConstructor
public class SolanaWalletRepository {

    private final JdbcTemplate clickhouseJdbcTemplate;

    public void saveWallet(SolanaWallet wallet) {
        String sql = "INSERT INTO wallets (wallet, prefix, suffix, private_key) VALUES (?, ?, ?, ?)";

        String address = wallet.getAddress();
        String prefix = address.substring(0, 3);
        String suffix = address.substring(address.length() - 3);

        String privateKey = Base64.getEncoder().encodeToString(wallet.getPrivateKey());

        clickhouseJdbcTemplate.update(
                sql,
                address,
                prefix,
                suffix,
                privateKey
        );
    }
}
