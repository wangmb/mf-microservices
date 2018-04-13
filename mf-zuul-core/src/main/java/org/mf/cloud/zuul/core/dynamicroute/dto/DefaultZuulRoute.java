package org.mf.cloud.zuul.core.dynamicroute.dto;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;

import lombok.Data;

/**
 * @ClassName: DefaultZuulRoute
 * @Description: ZuulRoute扩展
 * @author mb.wang  
 * @date 2018年1月9日 下午2:57:25
 * 
 */
@Data
public class DefaultZuulRoute extends ZuulRoute{
	private String serverName;
}
