package org.halle.constant;

/**
 * 在选举时的优先级常量定义
 */
public class ElectionPriority {
    /**
     * Priority -1 represents this node disabled the priority election function.
     */
    public static final int Disabled   = -1;

    /**
     * Priority 0 is a special value so that a node will never participate in election.
     */
    public static final int NotElected = 0;

    /**
     * Priority 1 is a minimum value for priority election.
     */
    public static final int MinValue   = 1;
}
