package org.mf.cloud.zuul.core.ratelimit.support;

import org.mf.cloud.zuul.core.ratelimit.dto.RateLimitDTO;

/**
 * @ClassName: IRateLimitStore
 * @Description: 实时限流相关信息计算及存储接口
 * @author mb.wang  
 * @date 2018年1月10日 下午2:47:22
 * 
 */
public interface IRateLimitStore {
	
	/**
	 * @Title: serviceExists
	 * @Description: 判断微服务是否配置。配置返回true，为配置返回false
	 * @author mb.wang  
	 * @date 2018年1月10日 下午5:12:43
	 * @param serviceId
	 * @return boolean
	 */
	boolean serviceExists(String serviceId);
	
	/**
	 * @Title: getRateLimitByServiceId
	 * @Description: 根据服务id查询该服务对应的限流相关信息
	 * @author mb.wang  
	 * @date 2018年1月10日 下午2:49:07
	 * @param serviceId
	 * @return RateLimitDTO 
	 */
	RateLimitDTO getRateLimitByServiceId(String serviceId);
	
	/**
	 * @Title: calculateAndStoreRateLimit
	 * @Description: 计算当前服务是否满足流程现在要求并存储计算结果，满足返回true，否则返回false.
	 * @author mb.wang  
	 * @date 2018年1月10日 下午2:52:55
	 * @param rateLimitDTO 
	 * @param accessCode  唯一码，通过IRateLimit接口方法(getAccessCode)生成
	 * @return boolean  
	 */
	boolean calculateAndStoreRateLimit(RateLimitDTO rateLimitDTO,String accessCode);
}
