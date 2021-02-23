package org.halle.core;

import com.alipay.remoting.rpc.RpcClient;
import com.alipay.remoting.rpc.RpcServer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.halle.constant.Constant;
import org.halle.rpc.RequestBody;
import org.halle.rpc.SimpleClientUserProcessor;
import org.halle.rpc.SimpleServerUserProcessor;
import org.halle.time.ScheduleManager;
import org.halle.time.Timer;
import org.halle.time.TimerFactory;
import org.halle.util.Requires;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class Node {
    private static final Logger logger         = LoggerFactory
            .getLogger(Node.class);

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
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock;
    private Lock writeLock;
    private Node(){

    }


    private Node (String ip,String port,String index){
        this.index=index;
        this.port=port;
        this.ip=ip;

    }

    public Node (int port,String index ,List<String> nodes,int timerThreadNum){
        this.index=index;
        this.port=String.valueOf(port);
        this.ip=Constant.LOCAL_ANY_IP;
        this.rpcClient=new RpcClient();
        rpcClient.registerUserProcessor(new SimpleClientUserProcessor(this));
        this.rpcServer=new RpcServer(port);
        rpcServer.registerUserProcessor(new SimpleServerUserProcessor(this));
        this.scheduleManager=new ScheduleManager(timerThreadNum);
        this.timerFactory=new TimerFactory(scheduleManager);
        this.electionTimer=timerFactory.getElectionTimeout(this);
        this.heartbeatTimer=timerFactory.getHeartBeatTimeout(this);
        this.readLock=readWriteLock.readLock();
        this.writeLock=readWriteLock.writeLock();
        this.voteConfig=new VoteConfig();
        this.otherNodes=convertStringToNode(nodes);
    }



    public boolean upgradeToCandidate(){
        if(writeLock.tryLock()){
            try{
                state = Constant.NODE_STATE_CANDIDATE;
                int term = this.voteConfig.increateTerm();
            }finally {
                writeLock.unlock();
            }
        }
        return true;
    }


    public void  start(){
        //启动选举任务
        this.electionTimer.start(adjustTimeout(this.voteConfig.getElectionTimeoutMs()));
    }


    public int adjustTimeout(int timeout){
        return timeout + (int)(Math.random() * (timeout+1000-timeout+1));
    }

    private List<Node> convertStringToNode(List<String> nodes){
        Requires.requireNonNull(nodes);
        return nodes.stream().map(str->{
            String[] arr = str.split(":");
            return new Node(arr[0],arr[1],arr[2]);
        }).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println( new Node().adjustTimeout(1000) );
        }
    }
}
