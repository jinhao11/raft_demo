package org.halle.core;

import com.alipay.remoting.rpc.RpcClient;
import com.alipay.remoting.rpc.RpcServer;
import lombok.Builder;
import lombok.Data;
import org.halle.constant.Constant;
import org.halle.time.ScheduleManager;
import org.halle.time.Timer;
import org.halle.time.TimerFactory;

import java.util.List;

@Data
@Builder
public class Node {
    private String ip;
    private String port;
    private String index;
    private List<Node> otherNodes;
    private int state = Constant.NODE_STATE_UNINITIALIZED;
    private RpcClient rpcClient;
    private RpcServer rpcServer;
    private Timer electionTimer;
    private Timer heartbeatTimer;
    private TimerFactory timerFactory;
    private ScheduleManager scheduleManager ;
    private VoteConfig voteConfig;
    private long lastHeartbeatTimestamp;
    private Node(){

    }
    public Node Node(int port,String nodeName , List<String> nodes,int timerThreadNum){
        return Node.builder().index(Constant.LOCAL_ANY_IP+":"+port+":"+nodeName)
                .port(String.valueOf(port)).ip(Constant.LOCAL_ANY_IP)
                .otherNodes(null).rpcClient(new RpcClient()).voteConfig(new VoteConfig())
                .rpcServer(new RpcServer(port)).scheduleManager(new ScheduleManager(timerThreadNum))
                .timerFactory(new TimerFactory(scheduleManager))
                .electionTimer(timerFactory.getElectionTimeout(this))
                .heartbeatTimer(timerFactory.getHeartBeatTimeout(this)).build();
    }


    public void  init(){
        //启动选举任务
        this.electionTimer.start(this.voteConfig.getElectionTimeoutMs());
    }

    private List<Node> convertStringToNode(List<String> nodes){
        //todo
        return null;
    }
}
