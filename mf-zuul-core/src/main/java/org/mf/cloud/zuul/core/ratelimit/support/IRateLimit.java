package org.mf.cloud.zuul.core.ratelimit.support;

import javax.servlet.http.HttpServletRequest;

import org.mf.cloud.zuul.core.ratelimit.dto.RateLimitDTO;
import org.springframework.cloud.netflix.zuul.filters.Route;

/**
 * @ClassName: IRateLimit
 * @Description: 自定义流量控制接口。
 * @author mb.wang  
 * @date 2018年1月5日 下午5:45:35
 * 
 */
public interface IRateLimit {
	/**
	 * @Title: shouldFilter
	 * @Description: 流量限制执行条件
	 * @author mb.wang  
	 * @date 2018年1月5日 下午5:46:29
	 * @param request
	 * @return boolean 
	 */
	boolean shouldFilter(HttpServletRequest request);
	
	/**
	 * @Title: getAccessCode
	 * @Description: 自定义流量限制编码
	 * @author mb.wang  
	 * @date 2018年1月5日 下午5:49:34
	 * @param request
	 * @param route
	 * @param rateLimitDTO
	 * @return String  返回类型
	 */
	String getAccessCode(HttpServletRequest request,Route route,RateLimitDTO rateLimitDTO);
}
