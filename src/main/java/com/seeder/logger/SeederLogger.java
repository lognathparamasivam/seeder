package com.seeder.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SeederLogger {

	public void error(Class<?> clazz, String msg) {
		Logger logger = LoggerFactory.getLogger(clazz);
		logger.error(msg);

	}

	public void info(Class<?> clazz, String msg) {
		Logger logger = LoggerFactory.getLogger(clazz);
		logger.info(msg);
	}

	public void debug(Class<?> clazz, String msg) {
		Logger logger = LoggerFactory.getLogger(clazz);
		logger.debug(msg);
	}
}
