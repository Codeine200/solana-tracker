package com.github.codeine200.soltracker.remote;


import com.github.codeine200.soltracker.remote.request.RpcRemoteRequest;
import com.github.codeine200.soltracker.remote.response.BlockRemoteResponse;
import com.github.codeine200.soltracker.remote.response.SlotRemoteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "solanaRpcClient", url = "https://api.mainnet-beta.solana.com")
public interface SolanaRpcClient {

    @PostMapping
    SlotRemoteResponse getSlot(RpcRemoteRequest request);

    @PostMapping
    BlockRemoteResponse getBlock(@RequestBody RpcRemoteRequest request);
}
