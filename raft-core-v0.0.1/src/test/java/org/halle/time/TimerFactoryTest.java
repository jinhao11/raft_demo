package org.halle.time;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimerFactoryTest {

    @Test
    public void getElectionTimeout() throws Exception{
        TimerFactory timerFactory = new TimerFactory(new ScheduleManager(2));
        Timer timer = timerFactory.getElectionTimeout();
        timer.start(5);
        Thread.sleep(3000);
        timer.stop();
        Thread.sleep(1000);
        timer.start(2);
        Thread.sleep(3000);
        timer.start(2);
    }
}