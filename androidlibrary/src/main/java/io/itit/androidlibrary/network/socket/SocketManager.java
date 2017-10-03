package io.itit.androidlibrary.network.socket;

import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.hwangjr.rxbus.RxBus;
import com.orhanobut.logger.Logger;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.itit.androidlibrary.Consts;
import io.itit.androidlibrary.ITITApplication;

import static io.itit.androidlibrary.Consts.SOCKET_CONNECT_TIME_OUT;

/**
 * Created by Lee_3do on 2017/9/11.
 */

public class SocketManager {
    public Socket socket;
    public int requestId;
    public static SocketManager instance = new SocketManager();

    private Map<Integer, ServiceContext> contextMap = null;
    private List<ServiceContext> rspList;
    private List<ResponseMessage> pushList;
    private List<Integer> timeoutRequests = new ArrayList<>();
    public int nowCount = 0;

    private SocketManager() {
        this.requestId = 0;
        this.contextMap = new HashMap<>();
        this.rspList = new ArrayList<>();
        this.pushList = new ArrayList<>();
        this.socket = new Socket();

    }

    public static SocketManager getInstance() {
        return instance;
    }

    public void connect() throws IOException {
        if (socket.isConnected() && !socket.isClosed()) {
            try {
                socket.shutdownInput();
                socket.shutdownOutput();
                socket.close();
            }catch (Exception e){
                Logger.e(e,"关闭错误");
            }

        }
        socket = new Socket();
        socket.connect(new InetSocketAddress(ITITApplication.host, ITITApplication.port),
                SOCKET_CONNECT_TIME_OUT);
        nowCount++;
        new Thread(() -> receiving(nowCount)).start();
    }


    protected void receiving(int nowCount) {
        Looper.prepare();
        ResponseMessage rsp = new ResponseMessage();
        DataInputStream buf = null;
        int nowCountT = nowCount;
        while (nowCountT ==  this.nowCount) {
            Logger.v("check new  message is "+nowCountT);
            try {
                buf = new DataInputStream(socket.getInputStream());
                while (buf.available() < 4) {
                    Thread.sleep(100);
                }
                int bodyLength = buf.readInt();
                while (buf.available() < bodyLength) {
                    Thread.sleep(100);
                }
                rsp = new ResponseMessage();
                rsp.payloadType = buf.readShort();
                rsp.requestId = buf.readInt();
                rsp.timestamp = buf.readLong();
                rsp.statusCode = buf.readShort();
                int statusMsgLength = buf.readShort();
                int serviceIdLength = buf.readShort();
                //
                //读取statusMsg
                byte[] statusMsg = new byte[statusMsgLength];
                buf.readFully(statusMsg, 0, statusMsgLength);
                //读取serviceId
                byte[] serviceId = new byte[serviceIdLength];
                buf.readFully(serviceId, 0, serviceIdLength);
                //读取payload
                int payloadLength = bodyLength - 20 - statusMsgLength - serviceIdLength;
                byte[] payload = new byte[payloadLength];
                buf.readFully(payload, 0, payloadLength);
                rsp.serviceId = new String(serviceId, "utf-8");
                rsp.statusMsg = new String(statusMsg, "utf-8");
                rsp.payload = new String(payload, "utf-8");
                RxBus.get().post(Consts.BusAction.REC_MSG, rsp);

                Thread.sleep(500);
            } catch (InterruptedException | IOException e) {
                Logger.e(e, "");
                return;
            }
        }
    }

    public void send(RequestMessage message) throws IOException {
        Logger.v(JSON.toJSONString(message));
        OutputStream outputStream = socket.getOutputStream();
        String json = JSON.toJSONString(message.rps);
        byte[] body = json.getBytes("utf-8");
        int capacity = 12 + message.si.length() + body.length;
        ByteBuffer buf2 = ByteBuffer.allocate(capacity);
        buf2.putInt(capacity);
        buf2.putShort((short) 1);//MessageType:json
        buf2.putInt(++requestId);
        buf2.putShort((short) message.si.length());
        buf2.put(message.si.getBytes());
        buf2.put(body);
        outputStream.write(buf2.array());
        outputStream.flush();
    }
}
