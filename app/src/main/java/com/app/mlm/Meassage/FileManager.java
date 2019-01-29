package com.app.mlm.Meassage;

import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;

public class FileManager {

    public static final String BASE_PATH = "/mnt/sdcard/ZGL";
    public static final String BIN = "/bin";
    public static final String Config = "/Config.properties";
    public static final String Login = "/Account.properties";
    public static final String BANNER = "/banner";
    public static final String LOCALBANNER = "/localbanner";
    public static final String LOG = "/logs";
    public static final String LOGDIR = "/logs/";
    public static final String WXCODE = "/mycode.png";
    public static String LOGINFO = "/logs/loginfo.txt";

    /**
     * 创建文件夹
     */
    public static boolean createFile() {
        boolean isCreated = false;
        try {
            //根目录
            File basefile = new File(BASE_PATH);
            if (!basefile.exists()) {
                basefile.mkdirs();
            }
            //配置文件
            File configfile = new File(BASE_PATH + Config);
            if (!configfile.exists()) {
                configfile.createNewFile();
            }
            //广告目录
            File bannerfile = new File(BASE_PATH + BANNER);
            if (!bannerfile.exists()) {
                bannerfile.mkdirs();
            }

            //本地广告目录
            File localbannerfile = new File(BASE_PATH + LOCALBANNER);
            if (!localbannerfile.exists()) {
                localbannerfile.mkdirs();
            }

            //本地bin目录
            File binF = new File(BASE_PATH + BIN);
            if (!binF.exists()) {
                binF.mkdirs();
            }

            //日志目录
            File logF = new File(BASE_PATH + LOG);
            if (!logF.exists()) {
                logF.mkdirs();
            }

            //二维码
            File wxCode = new File(BASE_PATH + WXCODE);
            if (!wxCode.exists()) {
                wxCode.createNewFile();
            }
            //log日志文件
//			File logfile = new File(BASE_PATH+LOGINFO);
//			if(!logfile.exists()){
//				logfile.createNewFile();
//			}
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            long time = System.currentTimeMillis();
            LOGINFO = "/logs/" + sdf.format(time) + ".txt";
            File logfile = new File(BASE_PATH + LOGINFO);
            if (!logfile.exists()) {
                logfile.createNewFile();
            }

            deleteLogfile();

            isCreated = true;
        } catch (Exception e) {
            e.printStackTrace();
            isCreated = false;
        }

        return isCreated;
    }

    public static void deleteLogfile() {
        File f = new File(FileManager.BASE_PATH + FileManager.LOG + "/");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        if (!f.exists())
            return;
        File[] fileList = f.listFiles();
        for (File file : fileList) {
            String filename = file.getName();
//			Log.e("main", "file: "+filename);
            //删除多余日志文件
            int dot = filename.lastIndexOf(".");
            String fileName = filename.substring(0, dot);
            Log.e("main", "file2: " + fileName);
            try {
                long curdate = System.currentTimeMillis();
                long logdate = df.parse(fileName).getTime();
                if ((curdate - logdate) >= (86400000l * 10)) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
                file.delete();
            }
//			for(int j = 0; j<listBanner.size();j++){
//				if(listBanner.get(j).getFileName().equals(file.getName())){
//					isExists = true;
//					break;
//				}
//			}
//			if(!isExists)
//				file.delete();

        }
    }
//	public static void downFile(File file,String url){
//		if(db.have(url)){
//			//存在 继续下载
//		}else{
//			//插入数据库
//			init(  insert );
//		}
//		down( updata);
//		
//		complete( delete );
//		
//	}
}
