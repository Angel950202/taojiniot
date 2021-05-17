package com.taojin.iot.transmit;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * 通信服务  bean spring 加载启动
 * 类型:CommunicatServer
 * ============================================================================
 * 版权所有 2013-2017无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 2017年12月23日
 * @author sjn
 * ============================================================================
 */
@Component
public class CommunicatServer{
	private static Logger logger = LoggerFactory
			.getLogger(CommunicatServer.class);
	private static List<Integer> ports = new ArrayList<Integer>();
	
	public static Integer lorawanIsStart = 0;//是否启动连接lorawan服务
	
	@PostConstruct
    public static void init(){
		
		ports.add(8035);
		new Thread(){
			@Override
			public void run(){
				Servers.startSocket(8035,3*60,"$$$");//启动tcp服务
			}
		}.start();
		
		ports.add(8033);
		new Thread(){
			@Override
			public void run(){
				Servers.startSocket(8033,3*60,"$$$");//启动tcp服务
			}
		}.start();
		
		
		/*ports.add(5002);
		new Thread(){
			@Override
			public void run(){
				Servers.startSocket(5002,3*60,"$$$");//启动tcp服务
			}
		}.start();
		ports.add(5003);
		new Thread(){
			@Override
			public void run(){
				Servers.startSocket(5003,3*60,"$$$");//启动tcp服务
			}
		}.start();
		ports.add(5004);
		new Thread(){
			@Override
			public void run(){
				Servers.startSocket(5004,3*60,"$$$");//启动tcp服务
			}
		}.start();
		ports.add(5005);
		new Thread(){
			@Override
			public void run(){
				Servers.startSocket(5005,3*60,"$$$");//启动tcp服务
			}
		}.start();
		ports.add(5006);
		new Thread(){
			@Override
			public void run(){
				Servers.startSocket(5006,3*60,"$$$");//启动tcp服务
			}
		}.start();
		*/
		/*new Thread(){
			@Override
			public void run(){
				Servers.startSocket(15801,3*60,"$$$");//启动udp服务
			}
		}.start();
		//用户中心
		ports.add(15802);
		new Thread(){
			@Override
			public void run(){
				Servers.startSocket(15802,3*60,"$$$");//启动tcp服务
			}
		}.start();
		ports.add(15803);
		new Thread(){
			@Override
			public void run(){
				Servers.startSocket(15803,3*60,"$$$");//启动udp服务
			}
		}.start();
		ports.add(15804);
		new Thread(){
			@Override
			public void run(){
				Servers.startSocket(15804,3*60,"$$$");//启动udp服务
			}
		}.start();*/
    }
	
    @PreDestroy
    public void destroy() {
    	for (Integer port : ports) {
    		Servers.closeSocket(port);
		}
    }
}
