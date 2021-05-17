package com.taojin.iot.redis;

import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * 类型:Util-连接和使用redis资源的工具类   
 * ============================================================================
 * 版权所有 2013-2014 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.UcSkype.com/
 * ----------------------------------------------------------------------------
 * date Jul 17, 2015 11:49:14 AM 
 * author 王杰
 * ============================================================================
 */
@Component
public class RedisUtil {
	/** logger */
	private static final Logger logger = Logger.getLogger(RedisUtil.class.getName());
	
	/**       
	 * 数据源      
	 */
	private JedisPool jedisPool;
	
	/**      
	 * 获取数据库连接       
	 * @return conn       
	 */     
	public Jedis getConnection() {
		Jedis jedis=null;    
		try {              
			jedis=jedisPool.getResource();          
		} catch(JedisConnectionException je){
			jedisPool.returnBrokenResource(jedis);
		} catch (Exception e) {
			logger.info("[redis连接异常]"+e.getMessage());
		}
		return jedis;      
	}   
	
	/**       
	 * 关闭数据库连接       
	 * @param conn       
	 */     
	public void closeConnection(Jedis jedis) {          
		if (null != jedis) {              
			try {
				jedisPool.returnResource(jedis);              
			} catch (Exception e) {
					e.printStackTrace();              
			}          
		}      
	}  
	
	/**       
	 * 设置连接池       
	 * @param 数据源      
	 */     
	public void setJedisPool(JedisPool JedisPool) {
		this.jedisPool = JedisPool;      
	}       
	
	/**       
	 * 获取连接池       
	 * @return 数据源       
	 */     
	public JedisPool getJedisPool() {
		return jedisPool;      
	}     
} 
