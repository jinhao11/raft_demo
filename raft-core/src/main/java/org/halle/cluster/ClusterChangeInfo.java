package org.halle.cluster;

import org.halle.common.LogId;
import org.halle.common.PeerId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class ClusterChangeInfo {
    private static final Logger LOG     = LoggerFactory.getLogger(ClusterChangeInfo.class);
    private LogId id      = new LogId(0, 0);
    private ClusterConfig       conf    = new ClusterConfig();
    private ClusterConfig       oldConf    = new ClusterConfig();
    public ClusterChangeInfo() {
        super();
    }
    public ClusterChangeInfo(LogId id, ClusterConfig conf, ClusterConfig oldConf) {
        this.id = id;
        this.conf = conf;
        this.oldConf = oldConf;
    }

    public LogId getId() {
        return this.id;
    }

    public void setId(final LogId id) {
        this.id = id;
    }

    public ClusterConfig getConf() {
        return conf;
    }

    public void setConf(ClusterConfig conf) {
        this.conf = conf;
    }

    public ClusterConfig getOldConf() {
        return oldConf;
    }

    public void setOldConf(ClusterConfig oldConf) {
        this.oldConf = oldConf;
    }

    public boolean isStable() {
        return this.oldConf.isEmpty();
    }

    public boolean isEmpty() {
        return this.conf.isEmpty();
    }

    public Set<PeerId> listPeers() {
        final Set<PeerId> ret = new HashSet<>(this.conf.listPeers());
        ret.addAll(this.oldConf.listPeers());
        return ret;
    }
    
}
