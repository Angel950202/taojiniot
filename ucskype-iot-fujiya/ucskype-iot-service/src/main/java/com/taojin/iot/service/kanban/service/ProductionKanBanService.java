package com.taojin.iot.service.kanban.service;


import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * @program: server
 * @Author: king
 * @Description: 生产监控看板
 * FileName: productionMonitorKanBanService
 * Date:     2019-08-05 0005 16:58:55
 */
public interface ProductionKanBanService {

    /**
     * 生产监控看板
     * @param line           产线
     * @param works          工位
     * @param Station        工站
     * @param okAddress      OK件地址        示例："C121-S1-DB201.DBW2,C121-S1-DB201.DBW2,C121-S1-DB201.DBW2"
     * @param nokAddress     NOK件地址       示例："C121-S1-DB201.DBW2,C121-S1-DB201.DBW2,C121-S1-DB201.DBW2"
     * @param totalAddress   生产总数地址     示例："C121-S1-DB201.DBW2,C121-S1-DB201.DBW2,C121-S1-DB201.DBW2"
     * @param lineCode 		  产线编码
     * @return
     */
    List splitScreenKanBan(String line,String lineCode,String works,String Station,String okAddress,String nokAddress,String totalAddress);


    /**
     * 设备状态
     * @param line          产线
     * @param works         工位
     * @param Station       工站          示例："工站1,工站2,工站3"
     * @param address       地址          示例："C101-S1-DB100.DBX5.5,C101-S1-DB100.DBX5.5"
     * @param lineCode
     * @return
     */
    List deviceStatus(String line,String lineCode,String works,String Station,String address);

    /**
     * 工站停机时间
     * @param line          产线
     * @param works         工位
     * @param Station       工站          示例："工站1,工站2,工站3"
     * @param address       地址          示例："C101-S1-DB100.DBX5.5,C101-S1-DB100.DBX5.5"
     * @param lineCode		产线编码
     * @param startTime
     * @param endTime
     * @return
     */
    Map downtime(String line,String lineCode,String works,String Station,String address,String startTime,String endTime);

    /**
     * 工作站报警次数
     * @param line          产线
     * @param works         工位
     * @param Station       工站          示例："工站1,工站2,工站3"
     * @param address       地址          示例："C101-S1-DB100.DBX5.5,C101-S1-DB100.DBX5.5"
     * @param lineCode		产线编码
     * @param startTime
     * @param endTime
     * @return
     */
    Map alarmNum(String line,String lineCode,String works,String Station,String address,String startTime,String endTime);

    /**
     * 工作站良品率
     * @param line          产线
     * @param works         工位
     * @param Station       工站          示例："工站1,工站2,工站3"
     * @param okAddress      OK件地址        示例："C121-S1-DB201.DBW2,C121-S1-DB201.DBW2,C121-S1-DB201.DBW2"
     * @param totalAddress   生产总数地址     示例："C121-S1-DB201.DBW2,C121-S1-DB201.DBW2,C121-S1-DB201.DBW2"
     * @param lineCode
     * @param startTime
     * @param endTime
     * @return
     */
    Map productLP (String line,String lineCode,String works,String Station,String okAddress,String totalAddress,String startTime,String endTime);
    
    /**
     * 不良品率
     * @param line
     * @param lineCode
     * @param works
     * @param Station
     * @param nokAddress
     * @param totalAddress
     * @param startTime
     * @param endTime
     * @return
     */
    Map productNLP (String line,String lineCode,String works,String Station,String nokAddress,String totalAddress,String startTime,String endTime);

    /**
     * trs趋势
     * @param line           产线
     * @param works          工位
     * @param Station        工站           示例："工站1,工站2,工站3"
     * @param totalAddress   生产总数地址     示例："C121-S1-DB201.DBW2,C121-S1-DB201.DBW2,C121-S1-DB201.DBW2"
     * @return
     */
    Map trsTrend(String line,String lineCode,String works,String Station,String totalAddress);

    /**
     * 工作站看板
     * @param line           产线
     * @param works          工位
     * @param Station        工站
     * @param okAddress      OK件地址        示例："C121-S1-DB201.DBW2,C121-S1-DB201.DBW2,C121-S1-DB201.DBW2"
     * @param nokAddress     NOK件地址       示例："C121-S1-DB201.DBW2,C121-S1-DB201.DBW2,C121-S1-DB201.DBW2"
     * @param totalAddress   生产总数地址     示例："C121-S1-DB201.DBW2,C121-S1-DB201.DBW2,C121-S1-DB201.DBW2"
     * @param armAddress     报警地址
     * @param taskorderCode	  任务单编码
     * @param lineCode		  产线编码
     * @param code			 工作站编码
     * @return
     */
    List workstationKanBan(String line,String works,String Station,String okAddress, String nokAddress,String totalAddress,String armAddress,String taskorderCode,String lineCode,String code);
    
    /**
     * 查询trs历史值
     * @param lineCode
     * @param okAddress
     * @param nokAddress
     * @param totalAddress
     * @return
     */
    
    JSONObject trsList(String lineCode,String okAddress,String nokAddress,String totalAddress);
}