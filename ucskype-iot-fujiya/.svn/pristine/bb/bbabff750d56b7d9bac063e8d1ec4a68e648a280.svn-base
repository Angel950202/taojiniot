package com.taojin.iot.transmit.bean;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.taojin.iot.transmit.utils.CRC16M;
import com.taojin.iot.transmit.utils.ConvertUtil;

/**
 * Bean-公共协议
 * @author wangjie
 *
 */
public class CommonCommand {
	
	/**头*/
	private String head;
	/**类型*/
	private String type;
	/**地址*/
	private String address;
	/**控制码*/
	private String controlCode;
	/**包长*/
	private String packLength;
	/**数据域*/
	private String data;
	/**crc校验*/
	private String crc;
	/**结束符*/
	private String end;
	
	public CommonCommand() {
		super();
	}

	public CommonCommand(String head, String type, String address,String controlCode,String data,
			String end) {
		super();
		this.head = head;
		this.type = type;
		this.address = address;
		this.controlCode = controlCode;
		this.packLength = ""+data.length()/2;
		this.data = data;
		this.end = end;
	}
	
	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getControlCode() {
		return controlCode;
	}

	public void setControlCode(String controlCode) {
		this.controlCode = controlCode;
	}

	public String getPackLength() {
		return packLength;
	}

	public void setPackLength(String packLength) {
		this.packLength = packLength;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCrc() {
		return crc;
	}

	public void setCrc(String crc) {
		this.crc = crc;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
	
	/**校验位设置*/
	private void checkCode(){
		StringBuilder needCheckStr = new StringBuilder();
		needCheckStr.append(head);
		needCheckStr.append(type);
		needCheckStr.append(address);
		needCheckStr.append(controlCode);
		needCheckStr.append(packLength);
		needCheckStr.append(data);
		this.crc = CRC16M.doCrc16(needCheckStr.toString());
	}
	
	private void getDataLength(){
		if(StringUtils.isBlank(data)){
			this.packLength = ConvertUtil.int2hex(0);
		}else{
			this.packLength = ConvertUtil.int2hex(data.length()/2);
		}
	}
	
	@Override
	public String toString() {
		this.getDataLength();
		checkCode();
		String command = head+type+address+controlCode+packLength+data+crc+end;
		return command;
	}
	
	/**
	 * 解析指令
	 * @param command 指令
	 * @return
	 */
	public void parse(String command){
		this.head = command.substring(0,2);//头
		this.type = command.substring(2,4);//类型
		this.address = command.substring(4,18);//地址
		this.controlCode = command.substring(18,20);//TODO 暂时为一个字节
		this.packLength = command.substring(20,22);//TODO 暂时为一个字节
		this.data = command.substring(22,command.length() - 6);//设备号
		this.crc = command.substring(command.length() - 6,command.length()-2);
		this.end = command.substring(command.length() - 2);
	}
	
	/**
	 * 16进制转long
	 * @param hexString
	 * @return
	 */
	public static Long hex2Ints(String hexString){
		 long dec_num = Long.parseLong(hexString, 16);  
	     return dec_num;
	}
	
	/**
	 * 16进制转int
	 * @param hexString
	 * @return
	 */
	public static Integer hex2Int(String hexString){
		 Integer dec_num = Integer.parseInt(hexString, 16); 
	     return dec_num;
	}
	
	/**
	 * 数据域解析 数量
	 * @param dataArea 数据域
	 * @return 
	 */
	public static String parseData(String dataArea){
		//String sensorAddress = dataArea.substring(46,50);//传感器地址
	//	int x = dataArea.length()-50;
		String data = dataArea.substring(2,10);//传感器数据
		String a = data.substring(1,2);
		String b = data.substring(3,4);
		String c = data.substring(5,6);
		String d = data.substring(7,8);
		return a + b + c +d;
	}
	public static void main(String args[]){
		System.out.println(parseData("0233353834034436"));
	}
	
}
