package org.halle.constant;

public class Constant {
    public final static int NODE_STATE_LEADER=1;
    public final static int NODE_STATE_FOLLOWER=2;
    public final static int NODE_STATE_CANDIDATE=3;
    public final static int NODE_STATE_UNINITIALIZED=-1;

    //心跳请求
    public final static int REQUEST_MSG_TYPE_HEARTBEAT=1;
    //请求投票
    public final static int REQUEST_MSG_TYPE_REQVOTE=1;

    public final static int RESPONSE_FAIL=-1;
    public final static int RESPONSE_SUCCESS=1;


    public final static String LOCAL_ANY_IP="0.0.0.0";



    //消息请求
    public final static String MSG_VOTE="please vote me!";
}
