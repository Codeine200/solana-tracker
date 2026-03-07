package com.github.codeine200.soltracker.remote.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockRemoteResponse {
    private String jsonrpc;
    private Block result;
    private int id;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Block {
        private long blockTime;
        private long blockHeight;
        private List<Transaction> transactions;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Transaction {
        private TransactionMessage transaction;
        private Meta meta;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionMessage {
        private Message message;
        private List<String> signatures;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {
        private List<String> accountKeys;
        private List<Instruction> instructions;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Instruction {
        private List<Integer> accounts;
        private Integer programIdIndex;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Meta {
        private Long fee;
        private Object err;
        private List<Long> preBalances;
        private List<Long> postBalances;
    }
}
