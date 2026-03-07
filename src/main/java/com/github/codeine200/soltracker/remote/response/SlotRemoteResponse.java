package com.github.codeine200.soltracker.remote.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlotRemoteResponse {
    private String jsonrpc;
    private long result;
    private int id;
}
