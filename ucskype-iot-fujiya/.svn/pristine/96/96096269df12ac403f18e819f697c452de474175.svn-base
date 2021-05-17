package com.taojin.iot.base.comm.listener;

import java.io.File;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.taojin.iot.base.comm.utils.TxtUtil;


/**
 * Listener - 初始化
 * 
 * 
 * 
 */
@Component("initListener")
public class InitListener implements ServletContextAware, ApplicationListener<ContextRefreshedEvent> {

	/** 安装初始化配置文件 */
	private static final String BANNER_FILE_PATH = "/banner.txt";

	/** logger */
	private static final Logger logger = Logger.getLogger(InitListener.class.getName());

	/** servletContext */
	private ServletContext servletContext;
	
	@Value("${system.name}")
	private String systemName;
	@Value("${system.version}")
	private String systemVersion;
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		if (servletContext != null && contextRefreshedEvent.getApplicationContext().getParent() == null) {
			
			String info = "I|n|i|t|i|a|l|i|z|i|n|g|  "+systemName+" | "+ systemVersion;
			logger.info(info.replace("|", ""));
			File bannerFile = new File(servletContext.getRealPath(BANNER_FILE_PATH));
			if (bannerFile.exists()) {
				System.out.println(TxtUtil.txt2String(bannerFile));
			}
		}
	}

}