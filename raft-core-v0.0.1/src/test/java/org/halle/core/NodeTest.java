package org.halle.core;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class NodeTest {
    String[] node1OtherNodes = {"127.0.0.1:8002:127.0.0.1-8002-02"};
    String[] node2OtherNodes = {"127.0.0.1:8001:127.0.0.1-8001-01"};

    @Test
    public void start() throws Exception{
       List<String> node1List =  Arrays.asList(node1OtherNodes);
       List<String> node2List =  Arrays.asList(node2OtherNodes);

       Node node1 = new Node(8001,"127.0.0.1:8001:127.0.0.1-8001-01",node1List,2);
       Node node2 = new Node(8002,"127.0.0.1:8002:127.0.0.1-8002-02",node2List,2);
       node1.start();
       node2.start();

       Thread.sleep(100000);
    }
}