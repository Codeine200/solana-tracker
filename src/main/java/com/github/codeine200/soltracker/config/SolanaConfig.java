package com.github.codeine200.soltracker.config;

import org.p2p.solanaj.rpc.RpcClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolanaConfig {

    @Bean
    public RpcClient rpcClient() {
        return new RpcClient("https://api.mainnet-beta.solana.com");
    }
}