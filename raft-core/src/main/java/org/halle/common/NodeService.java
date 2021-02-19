package org.halle.common;

import org.halle.cluster.ClusterConfig;
import org.halle.constant.Status;
import org.halle.option.NodeOptions;

/**
 * 节点
 */
public interface NodeService extends Lifecycle<NodeOptions>{
    PeerId getLeaderId();
    NodeId getNodeId();
    String getGroupId();
    NodeOptions getOptions();
    boolean isLeader();
    void shutdown(final Closure done);
    /**
     * Block the thread until the node is successfully stopped.
     *
     * @throws InterruptedException if the current thread is interrupted
     *         while waiting
     */
    void blockUntilStopped() throws InterruptedException;

    /**
     * 将任务数据应用到状态机中
     * @param task
     */
    void apply(final Task task);
    /**
     * Add a new peer to the raft group. done.run() would be invoked after this
     * operation finishes, describing the detailed result.
     *
     * @param peer peer to add
     * @param done callback
     */
    void addPeer(final PeerId peer, final Closure done);

    /**
     * Remove the peer from the raft group. done.run() would be invoked after
     * operation finishes, describing the detailed result.
     *
     * @param peer peer to remove
     * @param done callback
     */
    void removePeer(final PeerId peer, final Closure done);

    /**
     * Change the configuration of the raft group to |newPeers| , done.un()
     * would be invoked after this operation finishes, describing the detailed result.
     *
     * @param newPeers new peers to change
     * @param done     callback
     */
    void changePeers(final ClusterConfig newPeers, final Closure done);

    /**
     * Reset the configuration of this node individually, without any replication
     * to other peers before this node becomes the leader. This function is
     * supposed to be invoked when the majority of the replication group are
     * dead and you'd like to revive the service in the consideration of
     * availability.
     * Notice that neither consistency nor consensus are guaranteed in this
     * case, BE CAREFULE when dealing with this method.
     *
     * @param newPeers new peers
     */
    Status resetPeers(final ClusterConfig newPeers);
}

