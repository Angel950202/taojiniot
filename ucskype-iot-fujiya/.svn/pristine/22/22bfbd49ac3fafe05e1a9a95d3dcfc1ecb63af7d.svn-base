package com.taojin.iot.listener;

import java.util.logging.Logger;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.taojin.iot.redis.JedisUtil;
import com.taojin.iot.redis.JedisUtil.Hash;
import com.taojin.iot.transmit.CommunicatServer;

/**
 * Listener - socket初始化
 * 
 * 
 * 
 */
@Component("dtuServerListener")
public class ServerListener implements ServletContextAware,
		ApplicationListener<ContextRefreshedEvent> {

	/** logger */
	private static final Logger logger = Logger
			.getLogger(ServerListener.class.getName());

	/** servletContext */
	private ServletContext servletContext;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		if (servletContext != null
				&& contextRefreshedEvent.getApplicationContext().getParent() == null) {
			JedisUtil jedisUtil = new JedisUtil();
			Hash hash = jedisUtil.HASH;
			hash.hdel("iot_dtu_state");
			hash.hdel("equipment_session_sessionId");
			hash.hdel("equipment_session_equipmentId");
			hash.hdel("msgSendState");
			CommunicatServer.init();
		}
	}
}