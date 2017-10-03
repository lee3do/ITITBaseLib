package io.itit.androidlibrary.network.socket;

/**
 * Created by Lee_3do on 2017/9/11.
 */

public class ResponseMessage {
    public int requestId;
    public short payloadType;
    public String serviceId;
    public long timestamp;
    public int statusCode;
    public String statusMsg;
    public String payload;
}
