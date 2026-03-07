package com.github.codeine200.soltracker.service;

import com.github.codeine200.soltracker.mapper.SlotMapper;
import com.github.codeine200.soltracker.remote.request.RpcRemoteRequest;
import com.github.codeine200.soltracker.remote.response.BlockRemoteResponse;
import com.github.codeine200.soltracker.dto.response.SlotResponseDto;
import com.github.codeine200.soltracker.dto.response.SlotTransactionsResponseDto;
import com.github.codeine200.soltracker.dto.response.SolanaTransactionResponseDto;
import com.github.codeine200.soltracker.remote.SolanaRpcClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SolanaService {

    private final SolanaRpcClient solanaRpcClient;
    private final SlotMapper mapper;
    private static final double LAMPORTS_PER_SOL = 1_000_000_000.0;
    private static final String SYSTEM_PROGRAM_ID = "11111111111111111111111111111111";

    public SlotResponseDto getLastSlot() {
        RpcRemoteRequest request = new RpcRemoteRequest(
                "2.0",
                "getSlot",
                List.of(Map.of("commitment", "finalized")),
                1
        );
        return mapper.toDto(solanaRpcClient.getSlot(request));
    }

    public List<SolanaTransactionResponseDto> getTransactionsBySlot(long slot) {
        BlockRemoteResponse response = solanaRpcClient.getBlock(
                new RpcRemoteRequest(
                        "2.0",
                        "getBlock",
                        List.of(
                                slot,
                                Map.of(
                                        "encoding", "json",
                                        "transactionDetails", "full",
                                        "rewards", false,
                                        "maxSupportedTransactionVersion", 0
                                )
                        ),
                        1
        ));

        if (response.getResult() == null || response.getResult().getTransactions() == null) {
            return Collections.emptyList();
        }

        return response.getResult().getTransactions().stream()
                // Пропускаем транзакции с ошибкой
                .filter(tx -> tx.getMeta() != null && tx.getMeta().getErr() == null)
                .flatMap(tx -> {
                    List<String> accountKeys = tx.getTransaction().getMessage().getAccountKeys();
                    List<Long> preBalances = tx.getMeta().getPreBalances();
                    List<Long> postBalances = tx.getMeta().getPostBalances();

                    // Фильтруем инструкции, где участвует системный аккаунт
                    return tx.getTransaction().getMessage().getInstructions().stream()
                            .filter(instr -> {
                                Integer programIdIndex = instr.getProgramIdIndex();
                                if (programIdIndex == null || accountKeys.size() <= programIdIndex) return false;
                                String programId = accountKeys.get(programIdIndex);
                                return SYSTEM_PROGRAM_ID.equals(programId);
                            })
                            .map(instr -> {
                                // Берём индексы участников
                                List<Integer> accounts = instr.getAccounts();
                                if (accounts.size() < 2) return null; // должно быть хотя бы sender и receiver
                                int senderIdx = accounts.get(0);
                                int receiverIdx = accounts.get(1);

                                String sender = accountKeys.size() > senderIdx ? accountKeys.get(senderIdx) : null;
                                String receiver = accountKeys.size() > receiverIdx ? accountKeys.get(receiverIdx) : null;

                                long lamportsSent = (preBalances.get(senderIdx) - postBalances.get(senderIdx));
                                long fee = tx.getMeta().getFee() != null ? tx.getMeta().getFee() : 0;
                                double amount = (lamportsSent - fee) / LAMPORTS_PER_SOL;

                                return new SolanaTransactionResponseDto(sender, receiver, amount);
                            })
                            .filter(Objects::nonNull);
                })
                .collect(Collectors.toList());
    }

}
