package org.halle.rpc;

public interface RpcClient {
    Message transferMsg(String dest,Message message);
}
