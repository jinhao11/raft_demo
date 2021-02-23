package org.halle.time;

import com.alipay.remoting.InvokeCallback;
import com.alipay.remoting.InvokeContext;
import com.alipay.remoting.exception.RemotingException;
import org.halle.constant.Constant;
import org.halle.core.Node;
import org.halle.rpc.ElectionMsg;
import org.halle.rpc.RequestBody;
import org.halle.rpc.Result;
import org.halle.util.Requires;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;

public class TimerFactory {
    private Logger logger = LoggerFactory.getLogger(TimerFactory.class);
    private ScheduleManager scheduleManager;
    private Timer electionTimer;
    private Timer heartBeatTimer;

    public TimerFactory(ScheduleManager scheduleManager) {
        this.scheduleManager = scheduleManager;
    }
    public Timer getElectionTimeout(Node node){
        if(electionTimer==null){
            electionTimer = new ElectionTimeout(node);
        }
        return electionTimer;
    }

    public Timer getHeartBeatTimeout(Node node){
        if(heartBeatTimer==null){
            heartBeatTimer = new HeartBeatTimeout(node);
        }
        return heartBeatTimer;
    }


    class ElectionTimeout implements Timer{
        ScheduledFuture schedule;
        private Node node;

        public ElectionTimeout(Node node) {
            this.node = node;
        }

        @Override
        public void doRun(Node node) {
            //选举超时时钟内，没有接收到心跳
            logger.info("node[{}] can't receive leader's heartbeat want join election",node.getIndex());
            //升级成为候选人
            node.upgradeToCandidate();
            List<Node> nodes = node.getOtherNodes();
            if(nodes == null || nodes.isEmpty()) {
                return ;
            }
            ElectionMsg msg = new ElectionMsg(Constant.MSG_VOTE,node.getVoteConfig().getCurrentTerm());
            RequestBody<ElectionMsg> requestBody =
                    new RequestBody(msg,node.getIndex(),new Date().getTime(),Constant.REQUEST_MSG_TYPE_REQVOTE);

            for (Node otherNode : nodes) {
                String address = otherNode.getIp()+":"+otherNode.getPort();
                try {
                    Result responseMsg = (Result)(node.getRpcClient().invokeSync(address,requestBody,node.getVoteConfig().getElectionTimeoutMs()));
                }catch (RemotingException e){
                    logger.error("election rpc error",e);
                }catch (InterruptedException ite){
                    logger.error("election rpc error",ite);
                }
            }


        }

        @Override
        public void start(long delayTime) {
            logger.info("ElectionTimeout do start");
            schedule =  TimerFactory.this.scheduleManager.schedule(()->doRun(node),delayTime);
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
        private Node node;

        public HeartBeatTimeout(Node node) {
            this.node = node;
        }
        @Override
        public void doRun(Node node) {
            //心跳任务
            logger.info("ElectionTimeout do run");
        }

        @Override
        public void start(long delayTime) {
            logger.info("ElectionTimeout do start");
            schedule =  TimerFactory.this.scheduleManager.scheduleAtFixedRate(()->doRun(this.node),delayTime);
        }

        @Override
        public void stop() {
            Requires.requireNonNull(schedule,"please execute start method before stop");
            schedule.cancel(true);
            logger.info("ElectionTimeout do stop");
        }
    }


}
