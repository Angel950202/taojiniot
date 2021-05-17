package com.taojin.iot.base.comm;

/**
 * 类型: 
 * ============================================================================
 * 版权所有 2013-2014 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.UcSkype.com/
 * ----------------------------------------------------------------------------
 * date Jun 18, 2015 11:52:34 AM 
 * author 邵骏晶
 * ============================================================================
 */

public class CaptchaBean {
	
	//错误次数上限
	public static final int UPPERLIMIT = 3; 	
	
	//语音验证码
	public static final int TYPE_voiceCode = 1;
	//找回密码
	public static final int TYPE_findPass = 2;

	//验证码
	private String code;
	//生成时间
	private Integer createDate; 
	//计数器
	private Integer check;
	//类型
	private Integer type;
	
	public CaptchaBean(){
		super();
	}
	
	/**
	 * 构造方法，生成时间在内的一系列初始参数
	 * @param size
	 */
	public  CaptchaBean(int size){
		super();
		this.code = RandomUtil.getCode(4, 0);
		this.createDate = (int)(System.currentTimeMillis()/1000);
		this.check = 0;
		this.type = TYPE_voiceCode;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Integer createDate) {
		this.createDate = createDate;
	}
	public Integer getCheck() {
		return check;
	}
	public void setCheck(Integer check) {
		this.check = check;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}     
}
