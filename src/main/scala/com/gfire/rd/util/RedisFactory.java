/**
 * Copyright (C) 2015- by gfire.cn
 */
package com.gfire.rd.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @name RedisFactory
 * @author chenyl
 * @date 2017-03-07 9:49
 */
public class RedisFactory {
	private static final JedisPoolConfig config = new JedisPoolConfig();
	private static JedisPool pool = null;
    private static final Logger log = LoggerFactory.getLogger(RedisFactory.class);
    private static String ip="127.0.0.1";
    private static int port = 16378;
	private static void init() {
		config.setMaxIdle(200);
		config.setMaxTotal(5120);
		config.setMaxWaitMillis(5000);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		pool = new JedisPool(config, ip, port, 60000);
	}

	public static synchronized Jedis getRedisInstance() {
		Jedis jedis = null;
		if(pool == null){
			init();
		}
		try {
			jedis = pool.getResource();
		} catch (JedisConnectionException e) {
			log.info("error",e.getMessage());
			if (jedis != null){
				jedis.close();
			}
		}
		return jedis;
	}
}
