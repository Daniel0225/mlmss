package com.app.mlm.Meassage;

import android.util.Log;

import com.app.mlm.Meassage.entity.AndroidVend;
import com.app.mlm.Meassage.entity.VendOutEntity;

public class MCBDetail {

    /**
     * 出货
     */
    public static void toShipment(AndroidVend vend) {
        // try {
        // // 模拟处理出货
        // Thread.sleep(5000);
        // } catch (InterruptedException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // 模拟测试下位机处理出货，并返回报告
        VendOutEntity vendout = new VendOutEntity();
        vendout.setHdId(Integer.parseInt(vend.getHd()));
        vendout.setNum(Integer.parseInt(vend.getNum()));
        vendout.setSnm(vend.getSnm());
        vendout.setDeviceId(Util.getLoginPropertiesValue("machineId"));
        vendout.setDevice(1);
        vendout.setStatus(0);
        vendout.setType(1);
        vendout.setCtime(System.currentTimeMillis());
        // 消息类中调用，此处只为测试
        parseShipmentReport(vendout);
    }

    /**
     * 上传数据库中的出货报告
     */
    public static void uploadDBReport() {
    /*	//删除空订单的数据
        MyApplication.dbManager.delectData("");
		// 查询数据库中的所有出货报告
		List<VendOutEntity> vendOutList = MyApplication.dbManager
				.queryFirst30();
		for (int i = 0; i < vendOutList.size(); i++) {
			parseShipmentReport(vendOutList.get(i));
		}*/
    }

    /**
     * 解析出货报告 (参数测试使用)
     */
    public static void parseShipmentReport(VendOutEntity vendout) {
        Log.d("main", "需处理的出货报告数据:" + vendout.toString());
        //订单号为空不处理
        if (Util.isEmpty(vendout.getSnm()))
            return;
        // 插入数据库
        //MyApplication.dbManager.insertData(vendout);
        // 上传服务器
		/*Map<String, String> params = new HashMap<String, String>();
		params.put("deviceId", vendout.getDeviceId());
		params.put("device", vendout.getDevice() + "");
		params.put("status", vendout.getStatus() + "");
		params.put("hdId", vendout.getHdId() + "");
		params.put("type", vendout.getType() + "");
		params.put("num", vendout.getNum() + "");
		params.put("discardNum", vendout.getDropnum() + "");
		params.put("snm", vendout.getSnm());
		params.put("weight", vendout.getWeight() + "");
		params.put("ctime", vendout.getCtime() + "");
		OkHttpUtil.getInstance(MyApplication.handler)
				.post(UrlUtil.BASEURL + UrlUtil.UP_REPORT, params,
						uploadVendCallback);*/
    }

    /**
     * 下位机状态(故障)
     */
	/*public static void parseStatus_R(StatusEntity status) {
		Log.d("main", "准备上传的故障数据:" + status.toString());
		// 本地存储log日志
		// String loginfo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		// .format(System.currentTimeMillis()) + " START_R\t";
		// Util.WriteFileData(FileManager.BASE_PATH + FileManager.LOGINFO,
		// loginfo);

		// 改为只上传，不存储本地
		Map<String, String> params = new HashMap<String, String>();
		params.put("deviceId", status.getDeviceId() + "");
		params.put("device", status.getDevice() + "");
		params.put("importance", status.getImportance() + "");
		params.put("code", status.getCode() + "");
		params.put("status", status.getStatus() + "");
		params.put("ctime", status.getCtime() + "");
		OkHttpUtil.getInstance(MyApplication.handler)
				.post(UrlUtil.BASEURL + UrlUtil.FAULT_REPORT, params,
						upAlarmCallback);
	}*/

    /**
     * 回复服务器 出货确认
     * 成功则出货，否则不给下位机出货指令
     */
	/*public static void ReVendoutOK(final Handler handler, final AndroidVend vend){
//		LOG.debug("收到出货，但不发送确认。。。。。");
		//插入数据库
	
		MyApplication.dbManager.insertVentOrder(vend.getSnm());
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("deviceId", Util.getLoginPropertiesValue("machineId"));
		params.put("snm", vend.getSnm());
		OkHttpUtil.getInstance(MyApplication.handler)
				.post(UrlUtil.BASEURL + UrlUtil.RE_VENDOUT, params,
						new DataCallback(){

					@Override
					public void requestFail(IOException e) {
						// TODO Auto-generated method stub
						Log.d("main", "出货确认失败: " + e.getMessage());
					}

					class VendTask extends TimerTask {				
						private int count = 0;
						@Override
						public void run() {
							// TODO Auto-generated method stub
							// TODO Auto-generated method stub
							Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, Util.getCurDate() +"Constants.ifNACK is " +Constants.ifNACK +"\r\n");
							
							if(!Constants.ifNACK) {
									MyApplication.dbManager.deleteVentOrder(vend.getSnm());
									Message msg = Message.obtain();
									msg.what = MsgType.SHIPMENT;
									Bundle bundle = new Bundle();
									bundle.putSerializable("beat", vend);
									msg.setData(bundle);
									handler.sendMessage(msg);
									Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, Util.getCurDate() +"no ACK,Resentvend"+"\r\n");
									count++;	
							}
							
							if((Constants.ifNACK)||(count >=5)) {
								cancel();
								Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, Util.getCurDate() +" task cancel\r\n");
								Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, Util.getCurDate() +"task count is" + count +  "\r\n");
								Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, Util.getCurDate() +"Constants.ifNACK is " +Constants.ifNACK +"\r\n");
								if(Constants.ifACK) {
									Constants.Vendnum = vend.getNum();
									Constants.Vendednum = 1;
									Message msgUI = new Message();
									msgUI.what = MsgType.UI_PAYED;
									handler.sendMessage(msgUI);
									
								}
							}																				
						}
					};
					
					
					@Override
					public void requestSuccess(String result) throws Exception {
						// TODO Auto-generated method stub
						Log.d("main", "result: " + result);
						BaseEntity baseData = JSON.parseObject(result, BaseEntity.class);
						String code = baseData.getCode();
						String data = baseData.getData();
						String msgStr = baseData.getMsg();
						
						if (code.equals("0")) {
							Log.d("main", "确认出货成功。");
							//删除数据库
							MyApplication.dbManager.deleteVentOrder(vend.getSnm());
							Message msg = Message.obtain();
							msg.what = MsgType.SHIPMENT;
							Bundle bundle = new Bundle();
							bundle.putSerializable("beat", vend);
							msg.setData(bundle);
							handler.sendMessage(msg);
							Constants.ifNACK = false;
							VendTask taskvend = new VendTask();
							Timer timervend = new Timer();
							timervend.schedule(taskvend, 5000, 1000);;
						}
						*//*} else {
							Util.WriteFileData(FileManager.BASE_PATH
									+ FileManager.LOGINFO, Util.getCurDate() + " "
									+ "not vendout_c to MCB,SNM is" + vend.getSnm() +" \r\n");
						}*//*
					}
					
					
					
					
				});
	}*/
	
	/*
	/**
	 * 回复服务器 出货确认
	 * 成功则出货，否则不给下位机出货指令
	 */
	/*public static void ReVendoutOK(final Handler handler, final AndroidVend vend){
//		LOG.debug("收到出货，但不发送确认。。。。。");
		//插入数据库
		class VendTask extends TimerTask {				
			private int count = 0;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, Util.getCurDate() +"Constants.ifNACK is " +Constants.ifNACK +"\r\n");
				
				if(!Constants.ifNACK) {
						MyApplication.dbManager.deleteVentOrder(vend.getSnm());
						Message msg = Message.obtain();
						msg.what = MsgType.SHIPMENT;
						Bundle bundle = new Bundle();
						bundle.putSerializable("beat", vend);
						msg.setData(bundle);
						handler.sendMessage(msg);
						Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, Util.getCurDate() +"no ACK,Resentvend"+"\r\n");
						count++;	
				}
				
				if((Constants.ifNACK)||(count >=5)) {
					cancel();
					Constants.Vendednum = 1;
					Constants.VendingSNM = vend.getSnm();
					Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, Util.getCurDate() +" task cancel\r\n");
					Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, Util.getCurDate() +"task count is" + count +  "\r\n");
					Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, Util.getCurDate() +"Constants.ifNACK is " +Constants.ifNACK +"\r\n");
					Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, Util.getCurDate() +"Constants.Vendednum is " +Constants.Vendednum +"\r\n");
					if(Constants.ifACK) {
						Constants.Vendnum = vend.getNum();
						Message msgUI = new Message();
						msgUI.what = MsgType.UI_PAYED;
						handler.sendMessage(msgUI);
						
					}
				}																				
			}
			
		};
		MyApplication.dbManager.insertVentOrder(vend.getSnm());
		
		if(vend.getSnm() == Constants.VendingSNM ){
			Util.WriteFileData(FileManager.BASE_PATH
					+ FileManager.LOGINFO, Util.getCurDate() + " "
					+ "SNM repeated,Constants.VendingSNM is "+ Constants.Vendednum + " ,vend.getSnm() is"+ vend.getSnm() +" \r\n");
		}else{
			Util.WriteFileData(FileManager.BASE_PATH
					+ FileManager.LOGINFO, Util.getCurDate() + " "
					+ "vendout requestSuccess" + vend.getSnm() +" \r\n");
			Log.d("main", "确认出货成功。");
			//删除数据库
			MyApplication.dbManager.deleteVentOrder(vend.getSnm());
			Message msg = Message.obtain();
			msg.what = MsgType.SHIPMENT;
			Bundle bundle = new Bundle();
			bundle.putSerializable("beat", vend);
			msg.setData(bundle);
			handler.sendMessage(msg);
			Constants.ifNACK = false;
			VendTask taskvend = new VendTask();
			Timer timervend = new Timer();
			timervend.schedule(taskvend, 5000, 1000);
		}
		
		String SNM = vend.getSnm();
		Map<String, String> params = new HashMap<String, String>();
		params.put("deviceId", Util.getLoginPropertiesValue("machineId"));
		params.put("snm", vend.getSnm());
		OkHttpUtil.getInstance(MyApplication.handler)
				.post(UrlUtil.BASEURL + UrlUtil.RE_VENDOUT, params,
						new DataCallback(){

					@Override
					public void requestFail(IOException e) {
						// TODO Auto-generated method stub
						Log.d("main", "出货确认失败: " + e.getMessage());
						Util.WriteFileData(FileManager.BASE_PATH
								+ FileManager.LOGINFO, Util.getCurDate() + " "
								+ "requestFail" + e.getMessage() +" \r\n");
					}
					/*
					class VendTask extends TimerTask {				
						private int count = 0;
						@Override
						public void run() {
							// TODO Auto-generated method stub
							// TODO Auto-generated method stub
							Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, Util.getCurDate() +"Constants.ifNACK is " +Constants.ifNACK +"\r\n");
							
							if(!Constants.ifNACK) {
									MyApplication.dbManager.deleteVentOrder(vend.getSnm());
									Message msg = Message.obtain();
									msg.what = MsgType.SHIPMENT;
									Bundle bundle = new Bundle();
									bundle.putSerializable("beat", vend);
									msg.setData(bundle);
									handler.sendMessage(msg);
									Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, Util.getCurDate() +"no ACK,Resentvend"+"\r\n");
									count++;	
							}
							
							if((Constants.ifNACK)) {
								cancel();
								Constants.Vendednum = 1;
								Constants.VendingSNM = vend.getSnm();
								Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, Util.getCurDate() +" task cancel\r\n");
								Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, Util.getCurDate() +"task count is" + count +  "\r\n");
								Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, Util.getCurDate() +"Constants.ifNACK is " +Constants.ifNACK +"\r\n");
								Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, Util.getCurDate() +"Constants.Vendednum is " +Constants.Vendednum +"\r\n");
								if(Constants.ifACK) {
									Constants.Vendnum = vend.getNum();
									Message msgUI = new Message();
									msgUI.what = MsgType.UI_PAYED;
									handler.sendMessage(msgUI);
									
								}
							}																				
						}
					};*/
					
					/*
					@Override
					public void requestSuccess(String result) throws Exception {
						// TODO Auto-generated method stub
						Log.d("main", "result: " + result);
						BaseEntity baseData = JSON.parseObject(result, BaseEntity.class);
						String code = baseData.getCode();
						String data = baseData.getData();
						String msgStr = baseData.getMsg();
						
						Util.WriteFileData(FileManager.BASE_PATH
								+ FileManager.LOGINFO, Util.getCurDate() + " "
								+ "requestSuccess" + data +msgStr +" \r\n");
						/*if (code.equals("0")) {
							if(vend.getSnm() == Constants.VendingSNM ){
								Util.WriteFileData(FileManager.BASE_PATH
										+ FileManager.LOGINFO, Util.getCurDate() + " "
										+ "SNM repeated,Constants.VendingSNM is "+ Constants.Vendednum + " ,vend.getSnm() is"+ vend.getSnm() +" \r\n");
							}else{
								Util.WriteFileData(FileManager.BASE_PATH
										+ FileManager.LOGINFO, Util.getCurDate() + " "
										+ "vendout requestSuccess" + vend.getSnm() +" \r\n");
								Log.d("main", "确认出货成功。");
								//删除数据库
								MyApplication.dbManager.deleteVentOrder(vend.getSnm());
								Message msg = Message.obtain();
								msg.what = MsgType.SHIPMENT;
								Bundle bundle = new Bundle();
								bundle.putSerializable("beat", vend);
								msg.setData(bundle);
								handler.sendMessage(msg);
								Constants.ifNACK = false;
								VendTask taskvend = new VendTask();
								Timer timervend = new Timer();
								timervend.schedule(taskvend, 5000, 1000);
							}
							
						}
						/*} else {
							Util.WriteFileData(FileManager.BASE_PATH
									+ FileManager.LOGINFO, Util.getCurDate() + " "
									+ "not vendout_c to MCB,SNM is" + vend.getSnm() +" \r\n");
						}*/
					/*}
					
					
					
					
				});
	}
	
	*/


    /**
     * 上传打卡报告
     */
	/*public static void upLoginReport(final Handler handler, UpLoginReport loginReport){
		Map<String, String> params = new HashMap<String, String>();
		params.put("vmCode", Util.getLoginPropertiesValue("machineId"));
		params.put("sn", loginReport.getSn());
		params.put("lastTime", loginReport.getLastTime());
		params.put("lastSaleNum", loginReport.getLastSaleNum());
		params.put("lastSaleUsage", loginReport.getLastSaleUsage());
		params.put("thisTime", loginReport.getThisTime());
		params.put("thisSaleNum", loginReport.getThisSaleNum());
		params.put("thisSaleUsage", loginReport.getThisSaleUsage());
		params.put("inputNum", loginReport.getInputNum());
		params.put("inputType", loginReport.getInputType());
		OkHttpUtil.getInstance(MyApplication.handler)
				.get(UrlUtil.BASEURL + UrlUtil.SIGN, params,
						new DataCallback(){

							@Override
							public void requestFail(IOException e) {
								// TODO Auto-generated method stub
								Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, "up login report fail"+"\r\n");
								LOG.error("fail:"+e.getMessage());
								Message msg = Message.obtain();
								msg.what = MsgType.SIGN;
								Bundle bundle = new Bundle();
								bundle.putString("result_str", "网络异常，请重试！");
								msg.setData(bundle);
								handler.sendMessage(msg);
							}

							@Override
							public void requestSuccess(String result)
									throws Exception {
								// TODO Auto-generated method stub
								LOG.error("result:"+result);
								BaseEntity baseData = JSON.parseObject(result, BaseEntity.class);
								String code = baseData.getCode();
								String data = baseData.getData();
								String msgStr = baseData.getMsg();
								if(code.equals("0")){
									Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, "up login report success"+"\r\n");
									Message msg = Message.obtain();
									msg.what = MsgType.SIGN;
									Bundle bundle = new Bundle();
									bundle.putString("result_str", "签到成功!");
									msg.setData(bundle);
									handler.sendMessage(msg);
								}else{
									Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, "up login report fail:"+msgStr+"\r\n");
									Message msg = Message.obtain();
									msg.what = MsgType.SIGN;
									Bundle bundle = new Bundle();
									bundle.putString("result_str", msgStr);
									msg.setData(bundle);
									handler.sendMessage(msg);
								}
							}});
	}*/

    /**
     * 上传出货报告监听
     */
/*	static DataCallback uploadVendCallback = new DataCallback() {

		@Override
		public void requestSuccess(String result) throws Exception {
			// TODO Auto-generated method stub
			Log.d("main", "result: " + result);
			BaseEntity baseData = JSON.parseObject(result, BaseEntity.class);
			String code = baseData.getCode();
			String data = baseData.getData();
			String msg = baseData.getMsg();
			if (code.equals("0")) {
				Log.d("main", "上报成功: " + msg);
				UpLoadReportEntity entity = JSON.parseObject(data,
						UpLoadReportEntity.class);
				Log.d("main", "count1: " + MyApplication.dbManager.getDBCount());
				// 删除数据库
				MyApplication.dbManager.delectData(entity.getSnm());
				Log.d("main", "count2: " + MyApplication.dbManager.getDBCount());
			} else {
				Log.d("main", "fail: " + msg);
			}
		}

		@Override
		public void requestFail(IOException e) {
			// TODO Auto-generated method stub
			Log.d("main", "上传出货报告失败");
		}
	};*/

    /**
     * 上传故障报告监听
     */
	/*static DataCallback upAlarmCallback = new DataCallback() {

		@Override
		public void requestFail(IOException e) {
			// TODO Auto-generated method stub
			Log.d("main", "上传故障失败"+e.getMessage());
		}

		@Override
		public void requestSuccess(String result) throws Exception {
			// TODO Auto-generated method stub
			Log.d("main", "result: " + result);
		}
	};*/


}
