package org.halle.time;

import java.util.concurrent.ScheduledFuture;

public abstract class AbstractTimeTask implements TimeTask{
    ScheduledFuture scheduledFuture;
    TimerManager timerManager =  new TimerManager(10);
    @Override
    public int adjustTimeout() {
        return 0;
    }

    @Override
    public void doRun() {
        //todo 各定时任务执行自定义逻辑
    }

    @Override
    public void start() {
         scheduledFuture =  timerManager.schedule(()->doRun(),adjustTimeout());
    }

    @Override
    public void stop() {
        boolean cancelResult = scheduledFuture.cancel(true);
    }

    @Override
    public void init() {
        start();
    }



}
