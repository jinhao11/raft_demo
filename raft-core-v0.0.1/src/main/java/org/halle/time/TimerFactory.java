package org.halle.time;

import org.halle.util.Requires;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledFuture;

public class TimerFactory {
    private Logger logger = LoggerFactory.getLogger(TimerFactory.class);
    private ScheduleManager scheduleManager;
    private Timer electionTimer;
    private Timer heartBeatTimer;

    public TimerFactory(ScheduleManager scheduleManager) {
        this.scheduleManager = scheduleManager;
    }
    public Timer getElectionTimeout(){
        if(electionTimer==null){
            electionTimer = new ElectionTimeout();
        }
        return electionTimer;
    }

    public Timer getHeartBeatTimeout(){
        if(heartBeatTimer==null){
            heartBeatTimer = new HeartBeatTimeout();
        }
        return heartBeatTimer;
    }


    class ElectionTimeout implements Timer{
        ScheduledFuture schedule;
        @Override
        public void doRun() {
            //选举超时之后的动作
            logger.info("ElectionTimeout do run");
        }

        @Override
        public void start(long delayTime) {
            logger.info("ElectionTimeout do start");
            schedule =  TimerFactory.this.scheduleManager.schedule(()->doRun(),delayTime);
        }

        @Override
        public void stop() {
            Requires.requireNonNull(schedule,"please execute start method before stop");
            schedule.cancel(true);
            logger.info("ElectionTimeout do stop");
        }
    }


    class HeartBeatTimeout implements Timer{
        ScheduledFuture schedule;
        @Override
        public void doRun() {
            //心跳任务
            logger.info("ElectionTimeout do run");
        }

        @Override
        public void start(long delayTime) {
            logger.info("ElectionTimeout do start");
            schedule =  TimerFactory.this.scheduleManager.scheduleAtFixedRate(()->doRun(),delayTime);
        }

        @Override
        public void stop() {
            Requires.requireNonNull(schedule,"please execute start method before stop");
            schedule.cancel(true);
            logger.info("ElectionTimeout do stop");
        }
    }
}
