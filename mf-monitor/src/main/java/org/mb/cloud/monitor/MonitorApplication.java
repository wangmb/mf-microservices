package org.mb.cloud.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @ClassName: MonitorApplication
 * @Description: 微服务监控
 * @author mb.wang  
 * @date 2018年1月17日 上午11:32:23
 * 
 */
@SpringBootApplication
@EnableEurekaClient
@EnableHystrixDashboard
public class MonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitorApplication.class, args);
	}

}