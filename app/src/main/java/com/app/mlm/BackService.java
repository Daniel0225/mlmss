package com.app.mlm;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.app.mlm.bean.AndroidHeartBeat;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Created by  on 2017/7/24.
 * 由于移动设备的网络的复杂性，经常会出现网络断开，如果没有心跳包的检测，
 * 客户端只会在需要发送数据的时候才知道自己已经断线，会延误，甚至丢失服务器发送过来的数据。
 */

public class BackService extends Service {
    /**
     * 服务器ip地址
     */
    //47.106.143.212:65132
    public static final String HOST = "47.106.143.212";// "192.168.1.21";//
    /**
     * 服务器端口号
     */
    public static final int PORT = 65132;
    /**
     * 服务器消息回复广播
     */
    public static final String MESSAGE_ACTION = "message_ACTION";
    /**
     * 服务器心跳回复广播
     */
    public static final String HEART_BEAT_ACTION = "heart_beat_ACTION";
    private static final String TAG = "danxx";
    /**
     * 心跳频率
     */
    private static final long HEART_BEAT_RATE = 3 * 1000;
    AndroidHeartBeat heart;
    /**
     * 读线程
     */
    private ReadThread mReadThread;
    private LocalBroadcastManager mLocalBroadcastManager;
    /***/
    private WeakReference<Socket> mSocket;
    // For heart Beat
    private Handler mHandler = new Handler();
    private long sendTime = 0L;
    /**
     * 心跳任务，不断重复调用自己
     */
    private Runnable heartBeatRunnable = new Runnable() {

        @Override
        public void run() {
            if (System.currentTimeMillis() - sendTime >= HEART_BEAT_RATE) {
                boolean isSuccess = sendMsg("HeartBeat");//就发送一个\r\n过去 如果发送失败，就重新初始化一个socket
                if (!isSuccess) {
                    Log.e("---", "重连");
                    mHandler.removeCallbacks(heartBeatRunnable);
                    mReadThread.release();
                    releaseLastSocket(mSocket);
                    new InitSocketThread().start();
                }
            }
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new InitSocketThread().start();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);

    }

    public boolean sendMsg(final String msg) {
        if (null == mSocket || null == mSocket.get()) {
            return false;
        }
        Log.e("---重连发送内容", "---");
        final Socket soc = mSocket.get();
        if (!soc.isClosed() && !soc.isOutputShutdown()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OutputStream os = soc.getOutputStream();
                       /* heart = new AndroidHeartBeat("0000051","heartbeat");
                        sendMsg(heart.toString());*/
                        heart = new AndroidHeartBeat();
                        heart.setVmCode("0000072");
                        heart.setBusType("heartbeat");
                        String json = new Gson().toJson(heart);
                        String message = json;
                        Log.e("---发送内容", heart.toString());
                        os.write(heart.toString().getBytes());
                        os.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("---发送失败", e.getMessage());
                    }
                }
            }).start();
            sendTime = System.currentTimeMillis();//每次发送成数据，就改一下最后成功发送的时间，节省心跳间隔时间
        } else {
            return false;
        }
        return true;
    }

    private void initSocket() {//初始化Socket
        try {
            Socket so = new Socket(HOST, PORT);
            mSocket = new WeakReference<Socket>(so);
            mReadThread = new ReadThread(so);
            mReadThread.start();
            mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//初始化成功后，就准备发送心跳包
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 心跳机制判断出socket已经断开后，就销毁连接方便重新创建连接
     *
     * @param mSocket
     */
    private void releaseLastSocket(WeakReference<Socket> mSocket) {
        try {
            if (null != mSocket) {
                Socket sk = mSocket.get();
                if (!sk.isClosed()) {
                    sk.close();
                }
                sk = null;
                mSocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(heartBeatRunnable);
        mReadThread.release();
        releaseLastSocket(mSocket);
    }

    class InitSocketThread extends Thread {
        @Override
        public void run() {
            super.run();
            initSocket();
        }
    }

    // Thread to read content from Socket
    class ReadThread extends Thread {
        private WeakReference<Socket> mWeakSocket;
        private boolean isStart = true;

        public ReadThread(Socket socket) {
            mWeakSocket = new WeakReference<Socket>(socket);
        }

        public void release() {
            isStart = false;
            releaseLastSocket(mWeakSocket);
        }

        @Override
        public void run() {
            super.run();
            Socket socket = mWeakSocket.get();
            Log.e("---", "收到成功1111");
            if (null != socket) {
                try {
                    Log.e("---", "收到成功");
                    InputStream is = socket.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int length = 0;
                    String message1 = new String(Arrays.copyOf(buffer,
                            length)).trim();
                    Log.e(TAG, message1);
                    while (!socket.isClosed() && !socket.isInputShutdown()
                            && isStart && ((length = is.read(buffer)) != -1)) {
                        if (length > 0) {
                            String message = new String(Arrays.copyOf(buffer,
                                    length)).trim();
                            Log.e(TAG, message);
                            //收到服务器过来的消息，就通过Broadcast发送出去
                            if (message.equals("ok")) {//处理心跳回复
                                Intent intent = new Intent(HEART_BEAT_ACTION);
                                mLocalBroadcastManager.sendBroadcast(intent);
                            } else {
                                //其他消息回复
                                Intent intent = new Intent(MESSAGE_ACTION);
                                intent.putExtra("message", message);
                                mLocalBroadcastManager.sendBroadcast(intent);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}