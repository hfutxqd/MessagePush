package xyz.imxqd.push;

import com.google.gson.Gson;

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
    private final String mHost;
    private final int mPort;
    private OnNewMessageListener mListener;
    private boolean isRunning = true;

    private Thread mMessageListenThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                connect(mHost, mPort);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mServer.isConnected() && !mServer.isClosed()) {
                System.out.println("isConnected");
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(mServer.getInputStream()));
                    while (isRunning) {
                        String str = in.readLine();
                        str = URLDecoder.decode(str, "UTF-8");
                        Gson gson = new Gson();
                        Message msg = gson.fromJson(str, Message.class);
                        mListener.onNewMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    });

    public Client(String host, int port, OnNewMessageListener listener) throws IOException {
        this.mHost = host;
        this.mPort = port;
        this.mListener = listener;

    }

    private void connect(String host, int port) throws IOException {
        mServer = new Socket(host, port);
    }

    public void start() {
        mMessageListenThread.start();
    }

    public void stop() {
        try {
            mServer.close();
            isRunning = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface OnNewMessageListener {
        void onNewMessage(Message message);
    }
}
