package org.halle.time;

public interface Timer {
    void doRun();
    void start(long delayTime);
    void stop();
}
