package xyz.imxqd.push;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URLDecoder;

/**
 * Created by imxqd on 2017/3/26.
 */
public final class Client {

    private Socket mServer;
    private OnNewMessageListener mListener;

    private Thread mMessageListenThread = new Thread(new Runnable() {
        @Override
        public void run() {
            if (mServer.isConnected() && !mServer.isClosed()) {
                System.out.println("isConnected");
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(mServer.getInputStream()));
                    while (true) {
                        String str = in.readLine();
                        str = URLDecoder.decode(str, "UTF-8");
                        Message msg = JSONObject.parseObject(str, Message.class);
                        mListener.onNewMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    });

    public Client(OnNewMessageListener listener) throws IOException {
        this.mListener = listener;

    }

    public void connect(String host, int port) throws IOException {
        mServer = new Socket(host, port);
    }

    public void start() {
        mMessageListenThread.start();
    }

    public interface OnNewMessageListener {
        void onNewMessage(Message message);
    }
}
