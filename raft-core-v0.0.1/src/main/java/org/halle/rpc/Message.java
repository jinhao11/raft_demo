package org.halle.rpc;

import lombok.Data;

@Data
public class Message {
    private String msg;
    private Long term;
}
