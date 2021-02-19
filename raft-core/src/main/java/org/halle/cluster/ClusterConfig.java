package org.halle.cluster;

import org.halle.common.Copiable;
import org.halle.common.PeerId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ClusterConfig implements Iterable<PeerId>, Copiable<ClusterConfig> {
    private static final Logger LOG             = LoggerFactory.getLogger(ClusterConfig.class);
    private List<PeerId> peers           = new ArrayList<>();

    @Override
    public ClusterConfig copy() {
        return null;
    }



    public boolean isValid(){
        return !this.peers.isEmpty();
    }

    public void reset(){
        this.peers.clear();
    }

    public boolean isEmpty() {
        return this.peers.isEmpty();
    }
    public int size() {
        return this.peers.size();
    }
    @Override
    public Iterator<PeerId> iterator() {
        return this.peers.iterator();
    }
    public Set<PeerId> getPeerSet() {
        return new HashSet<>(this.peers);
    }
    public List<PeerId> listPeers() {
        return new ArrayList<>(this.peers);
    }
    public List<PeerId> getPeers() {
        return this.peers;
    }
    public void setPeers(final List<PeerId> peers) {
        this.peers.clear();
        for (final PeerId peer : peers) {
            this.peers.add(peer.copy());
        }
    }
    public boolean addPeer(final PeerId peer) {
        return this.peers.add(peer);
    }
    public boolean removePeer(final PeerId peer) {
        return this.peers.remove(peer);
    }
    public boolean contains(final PeerId peer) {
        return this.peers.contains(peer);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.peers == null) ? 0 : this.peers.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ClusterConfig other = (ClusterConfig) obj;
        if (this.peers == null) {
            return other.peers == null;
        } else {
            return this.peers.equals(other.peers);
        }
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final List<PeerId> peers = listPeers();
        int i = 0;
        int size = peers.size();
        for (final PeerId peer : peers) {
            sb.append(peer);
            if (i < size - 1 ) {
                sb.append(",");
            }
            i++;
        }

        return sb.toString();
    }
}
