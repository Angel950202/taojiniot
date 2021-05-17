package com.taojin.iot.base.comm;

/**
 * 类型:dwz返回处理 
 * ============================================================================
 * 版权所有 2013-2014 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.UcSkype.com/
 * ----------------------------------------------------------------------------
 * date Dec 8, 2014 1:15:55 PM 
 * author 王杰
 * ============================================================================
 */

public class DwzReturn {

	/**
	 *"statusCode":"200",
	 *"message":"\u64cd\u4f5c\u6210\u529f",
	 *"tabid":"table, table-fixed",
	 *"closeCurrent":true,
	 *"forward":"",
	 *"forwardConfirm":""
	 */
	private String statusCode;//状态值 200成功，300失败  301超时
	private String message;//状态消息 
	private String tabid;//标签id
	private Boolean closeCurrent;//是否关闭标签
	private String forward;//跳转模式
	private String forwardConfirm;//跳转路径
	
	
	/**
	 * 返回状态值
	 * 200：成功，300：失败，301：超时
	 * @return
	 */
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	/**
	 * 返回状态消息 纯文本
	 * @return
	 */
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 标签ID navTabId,dialogId
	 * @return
	 */
	public String getTabid() {
		return tabid;
	}
	public void setTabid(String tabid) {
		this.tabid = tabid;
	}
	
	/**
	 * 是否关闭标签
	 * @return
	 */
	public Boolean getCloseCurrent() {
		return closeCurrent;
	}
	public void setCloseCurrent(Boolean closeCurrent) {
		this.closeCurrent = closeCurrent;
	}
	
	/**
	 * 跳转方式 forward 
	 * 此处为空，则forwardConfirm不起作用，此处不为空
	 * @return
	 */
	public String getForward() {
		return forward;
	}
	public void setForward(String forward) {
		this.forward = forward;
	}
	
	/**
	 * 跳转路径
	 * 必须配合forward使用
	 * @return
	 */
	public String getForwardConfirm() {
		return forwardConfirm;
	}
	public void setForwardConfirm(String forwardConfirm) {
		this.forwardConfirm = forwardConfirm;
	}
	
}
