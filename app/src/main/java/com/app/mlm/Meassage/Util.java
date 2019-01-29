package com.app.mlm.Meassage;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Util {
    private static Toast toast;

    static {
        File file = new File(FileManager.BASE_PATH);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void showToast(Context context,
                                 String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    /**
     * 字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str))
            return true;
        else
            return false;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurDateFMT() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(date);
    }

    public static String getVersion(Context context) {
        String verId = "";
        try {
            PackageManager packManager = context.getPackageManager();
            PackageInfo info = packManager.getPackageInfo(context.getPackageName(), 0);
            verId = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verId;
    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDPath() {
        String path = "";
        // /mnt/sdcard
        // path:/storage/emulated/0
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            // SD卡存在
            path = Environment.getExternalStorageDirectory().getPath();
        }
        return path;
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnect(Context context) {
        boolean isConnected = false;
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = manager.getActiveNetworkInfo();
        if (netInfo != null) {
            isConnected = netInfo.isAvailable();
        }
        return isConnected;
    }

    /**
     * 向配置文件中写属性
     *
     * @param parmas
     * @param value
     */
    public static void setProperties(String param, String value) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(getConfigFile()));
            OutputStream out = new FileOutputStream(getConfigFile());

            props.setProperty(param, value);
            props.store(out, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 向机器编码配置文件中写属性
     *
     * @param parmas
     * @param value
     */
    public static void setLoginProperties(String param, String value) {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(getLoginFile()));
            OutputStream out = new FileOutputStream(getLoginFile());

            props.setProperty(param, value);
            props.store(out, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 从配置文件中取值
     *
     * @param param
     * @return
     */
    public static String getPropertiesValue(String param) {
        String value = "";
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(getConfigFile()));
            value = props.getProperty(param);// 如果没有该属性 则返回 null 不是字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 从机器编码配置文件中取值
     *
     * @param param
     * @return
     */
    public static String getLoginPropertiesValue(String param) {
        String value = "";
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(getLoginFile()));
            value = props.getProperty(param);// 如果没有该属性 则返回 null 不是字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static File getConfigFile() {
        File config = null;
        try {
            config = new File(FileManager.BASE_PATH + FileManager.Config);
            if (!config.exists())
                config.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return config;
    }

    public static File getLoginFile() {
        File login = null;
        try {
            login = new File(FileManager.BASE_PATH + FileManager.Login);
            if (!login.exists())
                login.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return login;
    }

    public static File getMyFile(String filename) {
        File myfile = new File(filename);
        if (!myfile.exists()) {
            try {
                myfile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return myfile;
    }

    /**
     * 写消息到文本中
     *
     * @param filename
     * @param message
     */
    public static void WriteFileData(String filename, String message) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename, true);
            // byte[] bytes = message.getBytes();
            // fileOut.write(bytes);
            // fileOut.close();
            if (fileOut != null) {
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fileOut));
                bw.write(message);
                bw.flush();
                fileOut.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void WriteLog(String message) {
        WriteFileData(FileManager.BASE_PATH
                + FileManager.LOGINFO, Util.getCurDate() + " "
                + message + " \r\n");

    }


    /**
     * CHK算法
     *
     * @param msg
     * @param len
     * @return
     */
    public static short calc_crc(byte[] msg, short len) {
        short i, j;
        short crc = 0;
        short current;
        for (i = 0; i < len; i++) {
            current = (short) (msg[i] << 8);
            for (j = 0; j < 8; j++) {
                if ((short) (crc ^ current) < 0) {
                    crc = (short) ((crc << 1) ^ 0x1021);
                } else {
                    crc <<= 1;
                }
                current <<= 1;
            }
        }
        return crc;
    }

    /**
     * 字符串转byte数组
     *
     * @param s
     * @return
     */
    public static byte[] toHexBytes(String s) {
        byte[] bys = new byte[s.length()];
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            bys[i] = (byte) ch;
        }
        return bys;
    }


    /**
     * 计算输入流CRC32校验和
     *
     * @param f
     * @return -1 表示计算出错
     */
    public static long fileCrc32(File f) {
        byte[] arr = new byte[2048];
        FileInputStream is;
        try {
            is = new FileInputStream(f);
        } catch (FileNotFoundException e1) {
            return -1;
        }
        java.util.zip.CRC32 crc32 = new java.util.zip.CRC32();
        while (true) {
            try {
                int size = is.read(arr);
                if (size == -1) {
                    break;
                }
                crc32.update(arr, 0, size);
            } catch (IOException e) {
                try {
                    is.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                return -1;
            }
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        arr = null;
        return crc32.getValue();
    }

    /**
     * 将整数转成两位字符串
     *
     * @return
     */
    public static String toSecStr(int param) {
        String str = "00";
        if (param >= 10) {
            str = String.valueOf(param).substring(0, 2);
        } else if (param >= 0) {
            str = "0" + param;
        }
        return str;
    }
}
