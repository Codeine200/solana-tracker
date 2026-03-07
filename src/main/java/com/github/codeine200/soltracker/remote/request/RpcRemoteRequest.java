package com.github.codeine200.soltracker.remote.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcRemoteRequest {
    private String jsonrpc;
    private String method;
    private List<Object> params;
    private int id;
}