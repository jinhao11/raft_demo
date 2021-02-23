package org.halle.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.halle.constant.Constant;

@Data
@AllArgsConstructor
public class Result<T> {
    private int result;
    private String msg;
    private T data;

    public static Result getDefaultFailResult(String msg){
        return new Result(Constant.RESPONSE_FAIL,msg,null);
    }
    public static Result getDefaultSuccessResult(String msg){
        return new Result(Constant.RESPONSE_SUCCESS,msg,null);
    }
}
