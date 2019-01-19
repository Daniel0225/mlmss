package com.app.mlm.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.bean
 * @fileName : GoodsInfo
 * @date : 2019/1/3  20:36
 * @describe : TODO
 * @email : xing.luo@taojiji.com
 */
@Entity
public class GoodsInfo implements Serializable {
    private static final long serialVersionUID = 536871008L;
    @Id(autoincrement = true)
    private Long id;
    @Generated(hash = 2073534098)
    public GoodsInfo(Long id, int mdseId, String mdsePrice, String mdseName,
            String mdseUrl, String clCode, int clCapacity, int clcCapacity,
            String gamePrice, String gameTimeStart, String gameTimeEnd,
            int position) {
        this.id = id;
        this.mdseId = mdseId;
        this.mdsePrice = mdsePrice;
        this.mdseName = mdseName;
        this.mdseUrl = mdseUrl;
        this.clCode = clCode;
        this.clCapacity = clCapacity;
        this.clcCapacity = clcCapacity;
        this.gamePrice = gamePrice;
        this.gameTimeStart = gameTimeStart;
        this.gameTimeEnd = gameTimeEnd;
        this.position = position;
    }
    @Generated(hash = 1227172248)
    public GoodsInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    private int mdseId;
    private String mdsePrice;
    private String mdseName;
    private String mdseUrl;
    private String clCode;
    private int clCapacity;
    private int clcCapacity;
    private String gamePrice;
    private String gameTimeStart;
    private String gameTimeEnd;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getMdseId() {
        return mdseId;
    }

    public void setMdseId(int mdseId) {
        this.mdseId = mdseId;
    }

    public String getMdsePrice() {
        return mdsePrice;
    }

    public void setMdsePrice(String mdsePrice) {
        this.mdsePrice = mdsePrice;
    }

    public String getMdseName() {
        return mdseName;
    }

    public void setMdseName(String mdseName) {
        this.mdseName = mdseName;
    }

    public String getMdseUrl() {
        return mdseUrl;
    }

    public void setMdseUrl(String mdseUrl) {
        this.mdseUrl = mdseUrl;
    }

    public String getClCode() {
        return clCode;
    }

    public void setClCode(String clCode) {
        this.clCode = clCode;
    }

    public int getClCapacity() {
        return clCapacity;
    }

    public void setClCapacity(int clCapacity) {
        this.clCapacity = clCapacity;
    }

    public int getClcCapacity() {
        return clcCapacity;
    }

    public void setClcCapacity(int clcCapacity) {
        this.clcCapacity = clcCapacity;
    }

    public String getGamePrice() {
        return gamePrice;
    }

    public void setGamePrice(String gamePrice) {
        this.gamePrice = gamePrice;
    }

    public String getGameTimeStart() {
        return gameTimeStart;
    }

    public void setGameTimeStart(String gameTimeStart) {
        this.gameTimeStart = gameTimeStart;
    }

    public String getGameTimeEnd() {
        return gameTimeEnd;
    }

    public void setGameTimeEnd(String gameTimeEnd) {
        this.gameTimeEnd = gameTimeEnd;
    }

}
