package com.taojin.iot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 类型:测试
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * ============================================================================
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseController{
	/**
	 * 查询公司资料
	 * @param requestParams
	 * requestParams={"action":"test","method":"testapi"}
	 * @return
	 */
	@RequestMapping(value="/t",method = RequestMethod.GET, produces="application/json; charset=utf-8")
	@ResponseBody
	public String testapi(){
		return successMsg("0", "test");
	}
}
