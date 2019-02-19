// BVMAidlInterface.aidl
package com.snbc.bvm;

// Declare any non-default types here with import statements

interface BVMAidlInterface {

     int BVMGetRunningState(int boxid);//获取整机状态
     int[] BVMGetDoorState(int boxid);//获取门状态
     String[] BVMGetFGFault(int boxid);//获取故障码
     int BVMCleanSysFault(int boxid);//故障确认
     int BVMInitXYRoad(int boxID, int xyselect, int workmode, int timeout);//货道扫描
     int[] BVMQueryInitResult(int boxID);//查询扫描结果
     int BVMQueryGoodsState(int boxID,int xSelect,int ySelect);//货道状态取得
     String BVMMoveSaleGoodsPro(String injson);//备货
     String BVMCtrlSaleGoodsStepPro(String injson);//备货后取货
     String BVMStartShip(String injson);//出货
     String BVMQueryLastSaleReport(String injson);//查询最后一次出货的出货报告
     int BVMReSaleGoods(int boxid);//客户未拿走货物时的再次开门
     int BVMSetWorkMode(int boxid,int state);//进入/退出维护模式
     int BVMWriteAssetCode(int boxid,String code);//烧录资产编码
     String BVMReadAssetCode(int boxid);//读取资产编码
     int BVMSetLightState(int boxID,int type ,int state); //设置灯光状态
     int BVMElecDoorCtrl(int boxID);//电控锁
     int BVMUpdateHardWare(int boxID,String filepath); //升级固件
     String  BVMQueryBoxInfo(int boxID,int type);//查询供应商代码，固件版本号等信息
     int BVMConnectTest(int boxid,int addr);//485测试
     String BVMOpenScanDev();//开始扫描
     int BVMCloseScanDev();//关闭设备扫描
     int BVMSetCtrlFGLayRow(int boxId, int nLay, int nRow, int state);//货道禁用/启用
     int[] BVMSGetFGLayRowState(int boxId, int nLay, int nRow);//货道禁用/启用查询
     int[] BVMGetGoodsLineRange(int boxid,int querytype,int positionX, int positionY);//获取宽高
     int BVMGetLaserPermission(int boxid);//是否支持激光测距
     int BVMSetKey(String key);//设置密钥
     int BVMSetColdHeatModel(int boxid,int mode);//制冷制热模式设置
     int BVMGetColdHeatModel(int boxid);//制冷制热模式查询
     int BVMSetColdModel(int boxid,int mode);//制冷模式设置
     int BVMGetColdMode(int boxid); //制冷模式查询
     int BVMSetColdTemp(int boxid,int onTemp,int offTemp);//制冷温度设置
     int[] BVMGetColdTemp(int boxid);//制冷温度查询
     int BVMSetHeatTemp(int boxid,int onTemp,int offTemp);//制热温度设置
     int[] BVMGetHeatTemp(int boxid); //制热温度查询
     int[] BVMGetColdHeatTemp(int boxid);//当前温度查询
     int BVMPowerHeartBeat(int boxid);//电源通讯检测


     int[]BVMInitResultYUANSHIARRAY(int boxID);//层列数查询
     int BVMSetXYInitBackZero(int boxID,int speed);//回初始化零点
     int BVMSetXYBackZero(int boxID,int speed);//回原点
     int GetXyRoad(int boxid,int speed,int positonX,int positionY);//到指定货道位置
}
