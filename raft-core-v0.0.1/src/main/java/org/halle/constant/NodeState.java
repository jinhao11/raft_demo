package org.halle.constant;

/**
 *  节点的选举状态
 */
public class NodeState {
    public final static int LEADER=1;
    public final static int FOLLOWER=2;
    public final static int CANDIDATE=3;
    public final static int UNINITIALIZED=-1;
}
