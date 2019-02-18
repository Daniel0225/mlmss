package com.app.mlm.http.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/2/18 0018.
 */

public class AdBean implements Serializable {

    /**
     * adName : 赠饮福利
     * url : http://47.106.143.212:8080/static/ad/c1003a28bb4a48ffaa9c72d387d1e257.jpg?ts=1550463920430
     * fileCode : 3202085223
     * fileType : 1
     * suffix : jpg
     * fileName : c1003a28bb4a48ffaa9c72d387d1e257.jpg
     */

    private String adName;
    private String url;
    private String fileCode;
    private int fileType;
    private String suffix;
    private String fileName;

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
