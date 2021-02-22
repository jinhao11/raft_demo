package org.halle.core;

import org.halle.constant.NodeState;

import java.util.List;

public class Node {
    private String ip="0.0.0.0";
    private String port;
    private String index;
    private List<Node> otherNodes;
    private int nodeState = NodeState.UNINITIALIZED;
    private VoteConfig vote;

    public Node(String port, String index, List<Node> otherNodes, int nodeState,VoteConfig vote) {
        this.port = port;
        this.index = index;
        this.otherNodes = otherNodes;
        this.nodeState = nodeState;
    }
}

