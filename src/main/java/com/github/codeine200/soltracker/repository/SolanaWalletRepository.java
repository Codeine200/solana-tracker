package com.github.codeine200.soltracker.repository;

import com.github.codeine200.soltracker.model.SolanaWallet;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Base64;
import java.util.List;

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

    public void saveBatch(List<SolanaWallet> wallets) {
        String sql = "INSERT INTO wallets (wallet, prefix, suffix, private_key) VALUES (?, ?, ?, ?)";

        clickhouseJdbcTemplate.batchUpdate(sql, wallets, 1000, (ps, w) -> {
            String addr = w.getAddress();

            ps.setString(1, addr);
            ps.setString(2, addr.substring(0, 3));
            ps.setString(3, addr.substring(addr.length() - 3));
            ps.setString(4, Base64.getEncoder().encodeToString(w.getPrivateKey()));
        });
    }
}
