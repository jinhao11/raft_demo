package org.halle.time;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimerManager {
    private final ScheduledExecutorService executor;

    /*public TimerManager(int workerThreads) {
        this.executor = executor;
    }*/

    public TimerManager(int workerThreads) {
        this.executor = new ScheduledThreadPoolExecutor(workerThreads,new NamedThreadFactory("raft-timerThreadPool"));
    }
    public TimerManager(int workerThreads, String threadGroupName) {
        this.executor = new ScheduledThreadPoolExecutor(workerThreads,new NamedThreadFactory(threadGroupName));
    }

    public ScheduledFuture<?> schedule(Runnable command, long timeout){
        return executor.schedule(command,timeout,TimeUnit.SECONDS);
    }


    public static void main(String[] args) {
        ScheduledExecutorService executor1 =  new ScheduledThreadPoolExecutor(1,new NamedThreadFactory("threadGroupName"));
        executor1.scheduleAtFixedRate(()-> System.out.println("我5秒一次"),0,5, TimeUnit.SECONDS);
    }


}
