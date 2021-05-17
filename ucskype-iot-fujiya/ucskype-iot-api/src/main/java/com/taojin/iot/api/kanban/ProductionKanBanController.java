package com.taojin.iot.api.kanban;

import javax.annotation.Resource;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.taojin.iot.BaseController;
import com.taojin.iot.service.kanban.service.ProductionKanBanService;

/**
 * @program: server
 * @author: king
 * @description: 生产看板controller
 * FileName: ProductionKanBanController
 * Date:     2019-08-05 0005 19:05:00
 **/
@Controller("productionKanBanController")
@RequestMapping("/kanBan")
public class ProductionKanBanController extends BaseController {

    @Resource(name = "productionKanBanServiceImpl")
    private ProductionKanBanService productionKanBanService;
    
    /**
     * 生产监控看板
     * @param line           产线
     * @param works          工位
     * @param Station        工站
     * @param okAddress      OK件地址        示例："C121-S1-DB201.DBW2,C121-S1-DB201.DBW2,C121-S1-DB201.DBW2"
     * @param nokAddress     NOK件地址       示例："C121-S1-DB201.DBW2,C121-S1-DB201.DBW2,C121-S1-DB201.DBW2"
     * @param totalAddress   生产总数地址     示例："C121-S1-DB201.DBW2,C121-S1-DB201.DBW2,C121-S1-DB201.DBW2"
     * @return
     */
    @RequestMapping(value = "/splitScreenKanBan", produces = "application/josn; charset=utf-8")
    @ResponseBody
    public String splitScreenKanBan(String line,String lineCode,String works,String Station,String okAddress,String nokAddress,String totalAddress) {
        return JSON.toJSONString(productionKanBanService.splitScreenKanBan(line,lineCode,works,Station,okAddress,nokAddress,totalAddress));
    }

    /**
     * 设备状态
     * @param line          产线
     * @param works         工位
     * @param Station       工站          示例："工站1,工站2,工站3"
     * @param address       地址          示例："C101-S1-DB100.DBX5.5,C101-S1-DB100.DBX5.5"
     * @return
     */
    @RequestMapping(value = "/deviceStatus", produces = "application/josn; charset=utf-8")
    @ResponseBody
    public String deviceStatus(String line,String lineCode,String works,String Station,String address) {
        return JSON.toJSONString(productionKanBanService.deviceStatus(line,lineCode,works,Station,address));
    }

    /**
     * 工站停机时间
     * @param line          产线
     * @param works         工位
     * @param Station       工站          示例："工站1,工站2,工站3"
     * @param address       地址          示例："C101-S1-DB100.DBX5.5,C101-S1-DB100.DBX5.5"
     * @return
     */
    @RequestMapping(value = "/downtime", produces = "application/josn; charset=utf-8")
    @ResponseBody
    public String downtime(String line,String lineCode,String works,String Station,String address,String startTime,String endTime) {
        return JSON.toJSONString(productionKanBanService.downtime(line,lineCode,works,Station,address,startTime,endTime));
    }

    /**
     * 工作站报警次数
     * @param line          产线
     * @param works         工位
     * @param Station       工站          示例："工站1,工站2,工站3"
     * @param address       地址          示例："C101-S1-DB100.DBX5.5,C101-S1-DB100.DBX5.5"
     * @return
     */
    @RequestMapping(value = "/alarmNum", produces = "application/josn; charset=utf-8")
    @ResponseBody
    public String alarmNum(String line,String lineCode,String works,String Station,String address,String startTime,String endTime) {
        return JSON.toJSONString(productionKanBanService.alarmNum(line,lineCode,works,Station,address,startTime,endTime));
    }

    /**
     * 工作站良品率
     * @param line           产线
     * @param works          工位
     * @param Station        工站          示例："工站1,工站2,工站3"
     * @param okAddress      OK件地址        示例："C121-S1-DB201.DBW2,C121-S1-DB201.DBW2,C121-S1-DB201.DBW2"
     * @param totalAddress   生产总数地址     示例："C121-S1-DB201.DBW2,C121-S1-DB201.DBW2,C121-S1-DB201.DBW2"
     * @return
     */
    @RequestMapping(value = "/productLP", produces = "application/josn; charset=utf-8")
    @ResponseBody
    public String productLP(String line,String lineCode,String works,String Station,String okAddress,String totalAddress,String startTime,String endTime) {
        return JSON.toJSONString(productionKanBanService.productLP(line,lineCode,works,Station,okAddress,totalAddress,startTime,endTime));
    }
    
    

    /**
     * 工作站良品率
     * @param line           产线
     * @param works          工位
     * @param Station        工站          示例："工站1,工站2,工站3"
     * @param nokAddress      不OK件地址        示例："C121-S1-DB201.DBW2,C121-S1-DB201.DBW2,C121-S1-DB201.DBW2"
     * @param totalAddress   生产总数地址     示例："C121-S1-DB201.DBW2,C121-S1-DB201.DBW2,C121-S1-DB201.DBW2"
     * @return
     */
    @RequestMapping(value = "/productNLP", produces = "application/josn; charset=utf-8")
    @ResponseBody
    public String productNLP(String line,String lineCode,String works,String Station,String nokAddress,String totalAddress,String startTime,String endTime) {
        return JSON.toJSONString(productionKanBanService.productNLP(line,lineCode,works,Station,nokAddress,totalAddress,startTime,endTime));
    }


    /**
     * trs趋势
     * @param line           产线
     * @param works          工位
     * @param Station        工站           示例："工站1,工站2,工站3"
     * @param totalAddress   生产总数地址     示例："C121-S1-DB201.DBW2,C121-S1-DB201.DBW2,C121-S1-DB201.DBW2"
     * @return
     */
    @RequestMapping(value = "/trsTrend", produces = "application/josn; charset=utf-8")
    @ResponseBody
    public String trsTrend(String line,String lineCode,String works,String Station,String totalAddress) {
        return JSON.toJSONString(productionKanBanService.trsTrend(line,lineCode,works,Station,totalAddress));
    }

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
    @RequestMapping(value = "/workstationKanBan", produces = "application/josn; charset=utf-8")
    @ResponseBody
    public String workstationKanBan(String line,String works,String Station,String okAddress, String nokAddress,String totalAddress,String armAddress,String taskorderCode,String lineCode,String code){
        return JSON.toJSONString(productionKanBanService.workstationKanBan(line,works,Station,okAddress,nokAddress,totalAddress,armAddress,taskorderCode,lineCode,code));
    }
    
    /**
     * trs历史趋势
     * @param requestParams
     * @return
     */
    @RequestMapping(value = "/trsList", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String trsList(String lineCode,String okAddress,String nokAddress,String totalAddress) {
    	
		return productionKanBanService.trsList(lineCode, okAddress, nokAddress, totalAddress).toString();
	}
}
 