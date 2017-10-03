package io.itit.androidlibrary.network.http;

/**
 * Created by Lee_3do on 2017/9/15.
 */

public class NeedLoginException extends RuntimeException {
    public NeedLoginException(){
        super("账户在别处登录,需要重新登录!");
    }
}
