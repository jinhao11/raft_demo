package org.halle.option;

import org.halle.common.Copiable;
import org.halle.constant.ElectionPriority;

/**
 * raft节点相关配置类
 */
public class NodeOptions extends RpcOptions implements Copiable<NodeOptions> {
    /**
     *超时举行选举的时间,当leader节点在该时间内部发送心跳
     * 则当前节点可以进行参与选举
     */
    private int electionTimeoutMs = 1000;

    private int electionPriority = ElectionPriority.Disabled;
    /**
     * 选举间隔，减少竞争消耗
     */
    private int  decayPriorityGap = 10;
    //TODO
    private int  leaderLeaseTimeRatio   = 90;
    //快照定期生成时间间隔
    private int snapshotIntervalSecs = 3600;

    //todo
    private int snapshotLogIndexMargin = 0;
    //todo
    private int catchupMargin = 1000;

}
