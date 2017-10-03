package io.itit.androidlibrary.network.socket;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee_3do on 2017/9/11.
 */

public class RequestMessage {
    public int ri;
    public String si;
    public List<String> rps;

    private RequestMessage(String si) {
        this.si = si;
        rps = new ArrayList<>();
    }

    //
    public static RequestMessage create(String si) {
        RequestMessage msg = new RequestMessage(si);
        return msg;
    }

    //
    public RequestMessage add(String parameter) {
        rps.add(parameter);
        return this;
    }

    //
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Message[").
                append("requestId=").append(ri).append(",").
                append("serviceId=").append(si).append(",").
                append("]");

        return sb.toString();
    }
}
