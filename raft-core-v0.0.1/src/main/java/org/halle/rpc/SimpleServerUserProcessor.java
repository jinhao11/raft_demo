/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.halle.rpc;

import com.alipay.remoting.BizContext;
import com.alipay.remoting.InvokeContext;
import com.alipay.remoting.NamedThreadFactory;
import com.alipay.remoting.rpc.protocol.SyncUserProcessor;
import org.apache.commons.lang.StringUtils;
import org.halle.constant.Constant;
import org.halle.core.Node;
import org.halle.util.Requires;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * a demo user processor for rpc server
 * 
 * @author xiaomin.cxm
 * @version $Id: SimpleServerUserProcessor.java, v 0.1 Jan 7, 2016 3:01:49 PM xiaomin.cxm Exp $
 */
public class SimpleServerUserProcessor extends SyncUserProcessor<RequestBody> {

    /** logger */
    private static final Logger logger         = LoggerFactory
                                                   .getLogger(SimpleServerUserProcessor.class);

    /** delay milliseconds */
    private long                delayMs;

    /** whether delay or not */
    private boolean             delaySwitch;

    /** executor */
    private ThreadPoolExecutor  executor;

    /** default is true */
    private boolean             timeoutDiscard = true;

    private AtomicInteger       invokeTimes    = new AtomicInteger();

    private AtomicInteger       onewayTimes    = new AtomicInteger();
    private AtomicInteger       syncTimes      = new AtomicInteger();
    private AtomicInteger       futureTimes    = new AtomicInteger();
    private AtomicInteger       callbackTimes  = new AtomicInteger();

    private String              remoteAddr;
    private CountDownLatch      latch          = new CountDownLatch(1);
    private Node currentNode;

    public SimpleServerUserProcessor() {
        this.delaySwitch = false;
        this.delayMs = 0;
        this.executor = new ThreadPoolExecutor(1, 3, 60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(4), new NamedThreadFactory("Request-process-pool"));
    }

    public SimpleServerUserProcessor(Node node) {
        this.delaySwitch = false;
        this.delayMs = 0;
        this.executor = new ThreadPoolExecutor(1, 3, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4), new NamedThreadFactory("Request-process-pool"));
        this.currentNode = node;
    }

    public SimpleServerUserProcessor(long delay) {
        this();
        if (delay < 0) {
            throw new IllegalArgumentException("delay time illegal!");
        }
        this.delaySwitch = true;
        this.delayMs = delay;
    }

    public SimpleServerUserProcessor(long delay, int core, int max, int keepaliveSeconds,
                                     int workQueue) {
        this(delay);
        this.executor = new ThreadPoolExecutor(core, max, keepaliveSeconds, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(workQueue), new NamedThreadFactory(
                "Request-process-pool"));
    }

    // ~~~ override methods

    @Override
    public Object handleRequest(BizContext bizCtx, RequestBody request) throws Exception {
        logger.warn("Request received:" + request + ", timeout:" + bizCtx.getClientTimeout()
                    + ", arriveTimestamp:" + bizCtx.getArriveTimestamp());
        if(request.getRequestType() == Constant.REQUEST_MSG_TYPE_REQVOTE){
            if(request.getData() instanceof ElectionMsg){
                ElectionMsg ele = (ElectionMsg)request.getData();
                if(StringUtils.isNotEmpty( currentNode.getVoteConfig().getVoteFor() )){
                    return Result.getDefaultFailResult("I am sorry, I was married");
                }
                if(ele.getTerm()<currentNode.getVoteConfig().getCurrentTerm()){
                    return Result.getDefaultFailResult("I am sorry,you are too young");
                }
                currentNode.getVoteConfig().setVoteFor(request.getFrom());
                return Result.getDefaultSuccessResult("Congratulations，you are admitted");
            }
        }
        return Result.getDefaultSuccessResult("Congratulations，you are admitted");

    }

    @Override
    public String interest() {
        return RequestBody.class.getName();
    }

    @Override
    public Executor getExecutor() {
        return executor;
    }

    @Override
    public boolean timeoutDiscard() {
        return this.timeoutDiscard;
    }

    // ~~~ public methods
    public int getInvokeTimes() {
        return this.invokeTimes.get();
    }


    public String getRemoteAddr() throws InterruptedException {
        latch.await(100, TimeUnit.MILLISECONDS);
        return this.remoteAddr;
    }

    // ~~~ private methods
    private void processTimes(RequestBody req) {
        this.invokeTimes.incrementAndGet();
        // TODO: 2021/2/23
    }

    // ~~~ getters and setters
    /**
     * Getter method for property <tt>timeoutDiscard</tt>.
     *
     * @return property value of timeoutDiscard
     */
    public boolean isTimeoutDiscard() {
        return timeoutDiscard;
    }

    /**
     * Setter method for property <tt>timeoutDiscard<tt>.
     *
     * @param timeoutDiscard value to be assigned to property timeoutDiscard
     */
    public void setTimeoutDiscard(boolean timeoutDiscard) {
        this.timeoutDiscard = timeoutDiscard;
    }
}