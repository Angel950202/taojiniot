package com.taojin.iot.api.transmit.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taojin.iot.BaseController;
import com.taojin.iot.transmit.handler.command.TcpUserCommandSendService;
import com.taojin.iot.transmit.handler.command.UserNbCommandSendService;
import com.taojin.iot.transmit.lib.CommunicatType;
/**
 * Controller-通信测试 iot物联网平台
 * ============================================================================
 * 版权所有 2013-2019 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午15:51:24 author 王杰
 * ============================================================================
 */
@Controller("internalTransmitTestController")
@RequestMapping("/internal/transmit/transmitTest")
public class TransmitTestController extends BaseController{
	
	@Resource(name="tcpUserCommandSendServiceImpl")
	private TcpUserCommandSendService tcpUserCommandSendService;
	@Resource(name="userNbCommandSendServiceImpl")
	private UserNbCommandSendService userNbCommandSendService;

	/**
	 * 获取用户信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/test", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String test(Integer state) {
		tcpUserCommandSendService.sendValve("89860402101840322930", "00000000001000", CommunicatType.USERTCP, state);
		return "success";
	}
	
	@RequestMapping(value = "/openVavle", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String openVavle(String imei,Integer state) {
		userNbCommandSendService.valveControl(imei, CommunicatType.USERNBUDP, state);
		return "success";
	}
	
	@RequestMapping(value = "/setReportCycle", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String setReportCycle(String imei,Integer cycle) {
		userNbCommandSendService.setupReportCycle(imei, CommunicatType.USERNBUDP, cycle);
		return "success";
	}
	
	@RequestMapping(value = "/setParams", produces = "application/josn; charset=utf-8")
	@ResponseBody
	public String setParams(String imei,String ip,String port) {
		String url = "udp://"+ip+":"+port;
		userNbCommandSendService.setParams(imei, CommunicatType.USERNBUDP, url);
		return "success";
	}
	
}
