package org.halle.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ElectionMsg {
    private int currentTerm;
}
