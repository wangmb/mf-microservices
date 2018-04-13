package org.mf.cloud.zuul.core.zuul.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @ClassName: ServiceZuulFallbackProvider
 * @Description: 网关hystrix回退统一异常处理类，当网关调用服务出现不可预知问题（比如服务不可访问）时，调用该接口。
 * @author mb.wang  
 * @date 2018年1月9日 上午10:37:28
 * 
 */
public class ServiceZuulFallbackProvider implements ZuulFallbackProvider {

	@Autowired(required=false)
	private ClientHttpResponse clientHttpResponse = null;
	
	@Override
	public String getRoute() {
		//设置期望那些服务ID具备回退能力，如果为*/null，则表示所有服务ID都支撑回退能力（hystrix）
		return null;
	}

	@Override
	public ClientHttpResponse fallbackResponse() {
		//请求服务失败，返回给客户端的信息。
		return clientHttpResponse!=null?clientHttpResponse :new ClientHttpResponse() {
			
			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders headers = new HttpHeaders();
				//和body中的内容编码一致，否则容易乱码  
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8); 
				return headers;
			}
			
			@Override
			public InputStream getBody() throws IOException {
				String str= "服务调用失败!";
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

}
