package org.mb.cloud.config.server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * @author wangmingbo
 *
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigApplication {
	private final static Logger LOG = LoggerFactory.getLogger(ConfigApplication.class);
	
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(ConfigApplication.class, args);

		LOG.info("[{}]已启动，端口：[{}]",ctx.getEnvironment().getProperty("spring.application.name")
				,ctx.getEnvironment().getProperty("server.port"));
		LOG.info("配置中心模式：[{}]，路径：[{}]",ctx.getEnvironment().getProperty("spring.profiles.active")
				,ctx.getEnvironment().getProperty("spring.cloud.config.server.native.searchLocations"));
//		LOG.info("rabbitmq地址：[{}]，端口：[{}]",ctx.getEnvironment().getProperty("spring.rabbitmq.host")
//				,ctx.getEnvironment().getProperty("spring.rabbitmq.port"));
	}
}