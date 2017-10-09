package io.itit.androidlibrary;

/**
 * Created by Lee_3do on 2017/9/11.
 */

public class Consts {

    public static class BusAction {
        public static final String LoginFailed = "LoginFailed";
        public static final String LoginSuccess = "LoginSuccess";
        public static final String SendMsg = "SendMsg";
        public static final String UpdateOrder = "UpdateOrder";
        public static final String ReceiveMsg = "ReceiveMsg";
        public static final String SendMsgResult = "SendMsgResult";
        public static final String UpdateMessage = "UpdateMessage";
        public static final String Socket_Reg = "Socket_Reg";
        public static final String REC_MSG = "REC_MSG";
        public static final String LOG_OUT = "LOG_OUT";
        public static final String Change_Brand = "Change_Brand";
        public static final String UpdateUnRead = "UpdateUnRead";
        public static final String TOAST = "TOAST";
        public static final String SHOW_LOADING = "SHOW_LOADING";
        public static final String PAY_FINISH = "PAY_FINISH";
    }

    public static class Service {
        public static final String SOCKET_LOGIN = "YxwlService.login";
        public static final String SOCKET_HeartBeat = "YxwlService.heartBeat";
    }

    public static class Intent {
        public static final String CHAT_USER = "chat_user";
        public static final String CHAT_ID = "chat_id";
        public static final String STAFF_ID = "STAFF_ID";
        public static final String Conversation_ID = "Conversation_ID";
        public static final int IMAGE_PICKER = 10086;

    }

    public static class OrderPage {
        public static final int P_待发货 = 0;
        public static final int P_已发货 = 1;
        public static final int P_已完成 = 2;
        public static final int P_今日已付款订单 = 3;
        public static final int P_今日待付款订单 = 4;
        public static final int P_待收款 = 5;
        public static final int P_已收款 = 6;
        public static final int P_今日成交总额 = 7;
        public static final int P_今日询单 = 8;

    }

    public static class Pref {
        public static final String NAME = "name";
        public static final String LOGIN = "login";
        public static final String HOST = "HOST";
        public static final String PORT = "PORT";
        public static final String LOGIN_ID = "loginId";
        public static final String DEVICE_ID = "deviceId";

        public static final String TOKEN = "TOKEN";

        public static final String REC_MESSAGE = "REC_MESSAGE";
        public static final String SOUND = "SOUND";
        public static final String VIBRATION = "VIBRATION";
    }

    public interface IMConstants {
        //常量设置
        long GAP_TIME = 5 * 60 * 1000;//五分钟,超过此时间,聊天时候显示时间
        long TIME_OUT = 30 * 1000;//三十秒,超过此时间,默认认为没发送到

        // 聊天
        String isFriend = "isFriend";
        String LoginState = "LoginState";
        String UserInfo = "UserInfo";
        String URL = "URL";
        String Title = "Title";
        String ID = "id";
        String CHAT_NAME = "NAME";//显示在聊天窗口上面的名字
        String AccessToken = "AccessToken";
        String PWD = "PWD";
        String ContactMsg = "ContactMsg";
        String VersionInfo = "VersionInfo";
        String IsMsg = "IsMsg";
        String User_ID = "User_ID";
        String conversation_ID = "CONVERSATION_ID";
        String CONVERSATION_NAME = "User_NAME";//显示在会话列表上的,名字
        String TYPE = "TYPE";
        String FROM = "FROM";

        String NET_ERROR = "网络错误，请稍后再试！";

        int REFRESH_LIST = 701;
        int REFRESH_ITEM = 702;
        int REFRESH_AND_SHOWTOAST = 703;
        int MSG_UPDATE_RECORD_DURATION = 704;
        int RECORD_STATUS_RECORD_STOP = 705;
        int RECORD_STATUS_RECORDING = 706;
        int DISMISS_RECORDER = 707;
        int SEND_PICTURES = 708;


        int FROM_NOTIFICATION = 1;


        String TEXT_PREFIX_STRING = "[TEXT_PREFIX_STRING]@";
        String IMG_PREFIX_STRING = "[IMG_PREFIX_STRING]@";
        String EXP_PREFIX_STRING = "[EXP_PREFIX_STRING]@";
        String VOICE_PREFIX_STRING = "[VOICE_PREFIX_STRING]@";
        String CLASS_NAME = "CLASS_NAME";
        String DUTY = "duty";
        String OPPOSITE_ID = "child_id";

    }


    public static class ErrorCode {
        public static final int TOKEN_EXP = 1001;
    }

    public static String ANDROID_PLATFORM = "2";
    public static String LOG_TAG = "ITIT";
    public static int SOCKET_CONNECT_TIME_OUT = 10 * 1000;
}
