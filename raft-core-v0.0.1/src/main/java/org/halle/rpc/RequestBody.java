package org.halle.rpc;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestBody<T> {
    private T data;
    private String from;
    private long timestamp;
    private int requestType;

}
