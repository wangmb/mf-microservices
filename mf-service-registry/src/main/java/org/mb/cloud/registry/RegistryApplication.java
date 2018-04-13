package org.mb.cloud.registry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @ClassName: RegistryApplication
 * @Description: 服务注册中心
 * @author mb.wang  
 * @date 2017年12月29日 上午9:37:11
 * 
 */
@EnableEurekaServer
@SpringBootApplication
public class RegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistryApplication.class, args);
	}
}