package org.mf.cloud.zuul.core.ratelimit;

/**
 * @ClassName: RateLimitType
 * @Description: 限流类型
 * @author mb.wang  
 * @date 2018年1月10日 上午11:14:52
 * 
 */
public enum RateLimitType {
	/**
	 * @Fields ORIGIN : 通过客户端IP地址区分
	 * @author mb.wang  
	 * @date 2018年1月10日 上午11:15:23
	 */
	ORIGIN, 
	/**
	 * @Fields USER : 通过登录用户名进行区分，也包括匿名用户
	 * @author mb.wang  
	 * @date 2018年1月10日 上午11:15:41
	 */
	USER,
	/**
	 * @Fields URL 通过请求路径区分
	 * @author mb.wang  
	 * @date 2018年1月10日 上午11:16:10
	 */
	URL
}
