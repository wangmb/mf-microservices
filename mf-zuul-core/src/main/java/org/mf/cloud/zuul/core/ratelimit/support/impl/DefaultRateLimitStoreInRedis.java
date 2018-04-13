package org.mf.cloud.zuul.core.ratelimit.support.impl;

import org.mf.cached.support.RedisDao;
import org.mf.cloud.zuul.core.config.properties.ZuulExtProperties;
import org.mf.cloud.zuul.core.ratelimit.dto.RateLimitDTO;
import org.mf.cloud.zuul.core.ratelimit.support.IRateLimitStore;

import lombok.RequiredArgsConstructor;

/**
 * @ClassName: DefaultRateLimitStoreInRedis
 * @Description: 实时限流相关信息计算及存储接口实现
 * @author mb.wang
 * @date 2018年1月10日 下午2:54:56
 * 
 */
@RequiredArgsConstructor
public class DefaultRateLimitStoreInRedis implements IRateLimitStore {
	private final ZuulExtProperties.RateLimit rateLimit;
	private final RedisDao redisDao;

	@Override
	public RateLimitDTO getRateLimitByServiceId(String serviceId) {
		Object obj = redisDao.get(rateLimit.getCachePrefix()+serviceId);
		RateLimitDTO rateLimitDTO = null;

		if (obj != null && obj instanceof RateLimitDTO) {
			rateLimitDTO = (RateLimitDTO) obj;
		}

		return rateLimitDTO;
	}

	@Override
	public boolean calculateAndStoreRateLimit(RateLimitDTO rateLimitDTO, String accessCode) {
		Long limit = rateLimitDTO.getLimit();
		Long refreshIntervalSeconds = rateLimitDTO.getRefreshIntervalSeconds();

		return (redisDao.incr(accessCode, refreshIntervalSeconds) >= limit) ? false : true;
	}

	@Override
	public boolean serviceExists(String serviceId) {
		return redisDao.exists(rateLimit.getCachePrefix()+serviceId);
	}

}
