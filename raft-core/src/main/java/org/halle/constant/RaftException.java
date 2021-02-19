package org.halle.constant;

public class RaftException extends Throwable{
    private static final long    serialVersionUID = -1533343555230409704L;
    private Status  status    = Status.OK();
    public RaftException() {
        super();
        this.status = Status.OK();
    }



}
