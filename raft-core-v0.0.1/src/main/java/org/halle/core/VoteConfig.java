package org.halle.core;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *   选举相关的参数类
 */

public class VoteConfig {
    private int  electionTimeoutMs = 1000;// follower to candidate timeout
    private String voteFor;
    private String leaderIndex;
    private AtomicInteger term = new AtomicInteger(0);

    public VoteConfig() {
    }

    public int  increateTerm(){
        return term.incrementAndGet();
    }


    public int getCurrentTerm(){
        return term.get();
    }

    public int getElectionTimeoutMs() {
        return electionTimeoutMs;
    }

    public void setElectionTimeoutMs(int electionTimeoutMs) {
        this.electionTimeoutMs = electionTimeoutMs;
    }

    public String getVoteFor() {
        return voteFor;
    }

    public void setVoteFor(String voteFor) {
        this.voteFor = voteFor;
    }

    public String getLeaderIndex() {
        return leaderIndex;
    }

    public void setLeaderIndex(String leaderIndex) {
        this.leaderIndex = leaderIndex;
    }
}
