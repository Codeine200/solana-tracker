package com.github.codeine200.soltracker.service;

import com.github.codeine200.soltracker.dto.response.SlotResponseDto;
import com.github.codeine200.soltracker.dto.response.SolanaTransactionResponseDto;
import com.github.codeine200.soltracker.mapper.SlotMapper;
import com.github.codeine200.soltracker.remote.SolanaRpcClient;
import com.github.codeine200.soltracker.remote.request.RpcRemoteRequest;
import com.github.codeine200.soltracker.remote.response.BlockRemoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private static final BigDecimal LAMPORTS_PER_SOL = new BigDecimal("1000000000");
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
                .filter(tx -> tx.getMeta() != null && tx.getMeta().getErr() == null)
                .flatMap(tx -> {
                    List<String> accountKeys = tx.getTransaction().getMessage().getAccountKeys();
                    List<Long> preBalances = tx.getMeta().getPreBalances();
                    List<Long> postBalances = tx.getMeta().getPostBalances();

                    return tx.getTransaction().getMessage().getInstructions().stream()
                            .filter(instr -> {
                                Integer programIdIndex = instr.getProgramIdIndex();
                                if (programIdIndex == null || accountKeys.size() <= programIdIndex) return false;
                                String programId = accountKeys.get(programIdIndex);
                                return SYSTEM_PROGRAM_ID.equals(programId);
                            })
                            .map(instr -> {
                                List<Integer> accounts = instr.getAccounts();
                                if (accounts.size() < 2) return null;
                                int senderIdx = accounts.get(0);
                                int receiverIdx = accounts.get(1);

                                String sender = accountKeys.size() > senderIdx ? accountKeys.get(senderIdx) : null;
                                String receiver = accountKeys.size() > receiverIdx ? accountKeys.get(receiverIdx) : null;

                                long lamportsSent = (preBalances.get(senderIdx) - postBalances.get(senderIdx));
                                long fee = tx.getMeta().getFee() != null ? tx.getMeta().getFee() : 0;
                                BigDecimal amount = BigDecimal.valueOf(lamportsSent)
                                        .subtract(BigDecimal.valueOf(fee))
                                        .divide(LAMPORTS_PER_SOL, 9, RoundingMode.DOWN);
                                if (amount.compareTo(BigDecimal.ZERO) <= 0) return null;

                                return new SolanaTransactionResponseDto(sender, receiver, amount);
                            })
                            .filter(Objects::nonNull);
                })
                .collect(Collectors.toList());
    }

}
