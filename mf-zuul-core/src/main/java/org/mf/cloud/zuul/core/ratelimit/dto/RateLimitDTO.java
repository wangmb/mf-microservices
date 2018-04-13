package org.mf.cloud.zuul.core.ratelimit.dto;

import java.util.HashSet;

import org.mf.cloud.zuul.core.ratelimit.RateLimitType;

import lombok.Data;

/**
 * @ClassName: RateLimitDTO
 * @Description: 限流控制实体类。
 * @author mb.wang  
 * @date 2018年1月10日 上午11:16:36
 * 
 */
@Data
public class RateLimitDTO {
	/**
	 * @Fields serverId : 限流服务ID
	 * @author mb.wang  
	 * @date 2018年1月10日 上午11:19:01
	 */
	private String serverId;
	/**
	 * @Fields type : 限流类型
	 * @author mb.wang  
	 * @date 2018年1月10日 上午11:16:52
	 */
	private HashSet<RateLimitType> types;
	
	/**
	 * @Fields limit : 请求限制
	 * @author mb.wang  
	 * @date 2018年1月10日 上午10:35:18
	 */
	private Long limit;
	
	/**
	 * @Fields refreshIntervalSeconds :刷新时间间隔（单位：秒）
	 * @author mb.wang  
	 * @date 2018年1月10日 上午10:39:23
	 */
	private Long refreshIntervalSeconds;
	
}
