package io.itit.androidlibrary.network.socket;

import java.util.Date;

/**
 * Created by Lee_3do on 2017/9/11.
 */

public class ServiceContext {
    public long time;
    public int requestId;
    public String serviceId;
    private ResponseMessage rspMessage;

    //
    public ServiceContext(int requestId, String serviceId) {
        this.requestId = requestId;
        this.serviceId = serviceId;
        this.time = new Date().getTime();
    }

    //
    public void SetRspMessage(ResponseMessage rspMessage) {
        this.rspMessage = rspMessage;
    }

    //
    public ResponseMessage GetRspMessage() {
        return rspMessage;
    }
}
