package org.halle.core;
/**
 *   选举相关的参数类
 */
public class VoteConfig {
    private int  electionTimeoutMs = 1000;// follower to candidate timeout
    private String voteFor;
    private String leaderIndex;
    private int term = 0;
}
