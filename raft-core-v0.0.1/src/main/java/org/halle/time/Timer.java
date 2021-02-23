package org.halle.time;

import org.halle.core.Node;

public interface Timer {
    void doRun(Node node);
    void start(long delayTime);
    void stop();
}
