package org.halle.time;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class TimerManager {
    private final ScheduledExecutorService executor;

    /*public TimerManager(int workerThreads) {
        this.executor = executor;
    }*/

    public TimerManager(int workerThreads, String threadGroupName) {
        this.executor = new ScheduledThreadPoolExecutor(workerThreads,new NamedThreadFactory(threadGroupName));
    }
}
