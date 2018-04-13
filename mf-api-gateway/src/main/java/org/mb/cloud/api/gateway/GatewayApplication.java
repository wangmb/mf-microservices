package org.mb.cloud.api.gateway;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.mb.cloud.api.gateway.filter.PreFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @ClassName: GatewayApplication
 * @Description: 微服务网关启动类
 * @author mb.wang  
 * @date 2018年1月9日 上午10:49:10
 * 
 */
@SpringBootApplication//(scanBasePackages="com.tydic")
@EnableEurekaClient
@EnableZuulProxy
//@EnableOAuth2Sso
public class GatewayApplication {//extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
	
	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}

	@Bean
	public ClientHttpResponse clientHttpResponse() {
		return new ClientHttpResponse() {
			
			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders headers = new HttpHeaders();
				//和body中的内容编码一致，否则容易乱码  
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8); 
				return headers;
			}
			
			@Override
			public InputStream getBody() throws IOException {
				String str= "服务调用失败(自定义)!";
				return new ByteArrayInputStream(str.getBytes("UTF-8"));
			}
			
			@Override
			public String getStatusText() throws IOException {
				return HttpStatus.OK.getReasonPhrase();
			}
			
			@Override
			public HttpStatus getStatusCode() throws IOException {
				/*网关向api服务请求失败，但用户客户端向网关发起的请求是OK的， 
	             * 不应该把api的404,500等问题抛给用户客户端 
	             * 网关和api服务集群对于客户端来说是黑盒子
	             */
				return HttpStatus.OK;
			}
			
			@Override
			public int getRawStatusCode() throws IOException {
				return 0;
			}
			
			@Override
			public void close() {
				
			}
		};
	}
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		// @formatter:off
//		System.out.println("============");
//		http.logout().and().authorizeRequests().antMatchers("/**/*.html", "/login").permitAll().anyRequest()
//				.authenticated().and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//		// @formatter:on
//	}
	
	
}