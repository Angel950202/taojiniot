/*package com.taojin.iot.api.task;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.taojin.iot.base.comm.utils.DatesUtils;
import com.taojin.iot.transmit.Servers;
import com.taojin.iot.transmit.bean.CommonCommand;
import com.taojin.iot.transmit.bean.InstructionsMap;
import com.taojin.iot.transmit.handler.TCPHandler;
import com.taojin.iot.transmit.lib.CommunicatType;
import com.taojin.iot.transmit.utils.HexUtil;

*//**
 * 一个定时任务类
 * 
 * 
 *//*
@EnableScheduling
@Component
@Lazy(false)
public class BlogTask {
	final static Logger logger = LoggerFactory.getLogger(BlogTask.class);
	public static int x = 283*4;
	public static int y = 285*4;
	public static int z = 55*4;

	// @Async
	@Scheduled(cron = "0 0/2 * * * ? ")
	public void testMiniteTask() {
		logger.info("当前连接数{}", TCPHandler.equipmentSession.size());
		if (TCPHandler.equipmentSession.size() != 0) {
			for (Entry<String, String> entry : TCPHandler.equipmentSession
					.entrySet()) {
				TCPHandler.SESSION_ID = entry.getKey();
				// logger.info("准备向{}发送指令", TCPHandler.SESSION_ID);
				for (Entry<String, String> it : InstructionsMap.INSTRUCTIONS_MAP_RC70
						.entrySet()) {
					if (TCPHandler.equipmentSession.size() == 0) {
						break;
					}
					TCPHandler.flag = it.getKey();
					CommonCommand commonCommand = new CommonCommand("68", "44",
							"AAAAAAAAAAAAAA", "07", it.getValue(), "23");
					Servers.tCPHandler.send(entry.getKey(),
							CommunicatType.SOCKET,
							HexUtil.toByteArray(commonCommand.toString()));
					logger.info("已向设备发{}送指令{}", entry.getKey(),
							commonCommand.toString());
					int x = 10;

					while (TCPHandler.flag != null) {
						try {
							Thread.sleep(800);
							x--;
							if (x == 0) {
								TCPHandler.flag = null;
								// y--;
							}
							// logger.info("超时倒数{}", x);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				for (Entry<String, String> it : InstructionsMap.INSTRUCTIONS_MAP_RD77
						.entrySet()) {
					if (TCPHandler.equipmentSession.size() == 0) {
						break;
					}
					TCPHandler.flag = it.getKey();
					CommonCommand commonCommand = new CommonCommand("68", "44",
							"AAAAAAAAAAAAAA", "07", it.getValue(), "23");
					Servers.tCPHandler.send(entry.getKey(),
							CommunicatType.SOCKET,
							HexUtil.toByteArray(commonCommand.toString()));
					logger.info("已向设备发{}送指令{}", entry.getKey(),
							commonCommand.toString());
					int x = 10;

					while (TCPHandler.flag != null) {
						try {
							Thread.sleep(800);
							x--;
							if (x == 0) {
								TCPHandler.flag = null;
								// y--;
							}
							// logger.info("超时倒数{}", x);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}

		}
	}

	//@Async
	@Scheduled(cron = "0/12 * * * * ? ")
	public void testMinite2Task() {
		x++;
	}
	
	
	//@Async
	@Scheduled(cron = "0/12 * * * * ? ")
	public void testMinite3Task() {
		y++;
	}
	
	//@Async
	@Scheduled(cron = "0 0/1 * * * ? ")
	public void testMinite4Task() {
		z++;
	}
	
	
}*/