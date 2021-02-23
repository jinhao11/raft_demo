package org.halle.time;

import org.halle.util.Requires;

import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class ScheduleManager {
    private final ScheduledExecutorService executor;
    public ScheduleManager(int workerThreads) {
        this.executor = new ScheduledThreadPoolExecutor(workerThreads,new NamedThreadFactory("raft-schedule-pool"));
    }
    public ScheduleManager(int workerThreads, String threadGroupName) {
        this.executor = new ScheduledThreadPoolExecutor(workerThreads,new NamedThreadFactory(threadGroupName));
    }

    public ScheduledFuture<?> schedule(Runnable command, long timeout) {
        Requires.requireNonNull(executor,"ScheduledExecutorService is null");
        return executor.schedule(command,timeout,TimeUnit.SECONDS);
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long period){
        Requires.requireNonNull(executor,"ScheduledExecutorService is null");
        return executor.schedule(command,period,TimeUnit.SECONDS);
    }
}
