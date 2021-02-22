package org.halle.time;

public interface TimeTask {
    int adjustTimeout();
    void doRun();
    void init();
    void start();
    void stop();
}
