package org.mf.cloud.zuul.core.ratelimit;

import static org.mf.cloud.zuul.core.config.properties.ZuulExtProperties.PREFIX;

import org.mf.cached.support.RedisDao;
import org.mf.cloud.zuul.core.config.properties.ZuulExtProperties;
import org.mf.cloud.zuul.core.ratelimit.filters.RateLimitPreFilter;
import org.mf.cloud.zuul.core.ratelimit.support.IRateLimit;
import org.mf.cloud.zuul.core.ratelimit.support.IRateLimitStore;
import org.mf.cloud.zuul.core.ratelimit.support.impl.DefaultRateLimit;
import org.mf.cloud.zuul.core.ratelimit.support.impl.DefaultRateLimitStoreInRedis;
import org.springframework.boot.actuate.endpoint.SystemPublicMetrics;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UrlPathHelper;

import com.netflix.zuul.ZuulFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: RateLimitAutoConfiguration
 * @Description: 限流自动配置
 * @author mb.wang  
 * @date 2018年1月10日 下午2:21:35
 * 
 */
@Configuration
@EnableConfigurationProperties(ZuulExtProperties.class)
@ConditionalOnProperty(prefix = PREFIX, name = "rate-limit.enabled", havingValue = "true",matchIfMissing=false)
@Slf4j
public class RateLimitAutoConfiguration {
	private final UrlPathHelper urlPathHelper = new UrlPathHelper();

	public RateLimitAutoConfiguration() {
		log.info("----激活流量限制----");
	}
	
	@Bean
	public ZuulFilter rateLimitPreFilter(IRateLimit rateLimit,IRateLimitStore rateLimitStore,RouteLocator routeLocator) {
		return new RateLimitPreFilter(rateLimit, rateLimitStore,routeLocator, urlPathHelper, true);
	}
	
	@Bean
	@ConditionalOnMissingBean(IRateLimitStore.class)
	public IRateLimitStore rateLimitStore(RedisDao redisDao,ZuulExtProperties zuulExtProperties) {
		return new DefaultRateLimitStoreInRedis(zuulExtProperties.getRateLimit(),redisDao);
	}
	
	@Bean
	@ConditionalOnMissingBean(value= {IRateLimit.class})
	public IRateLimit defaultRateLimit(SystemPublicMetrics systemPublicMetrics) {
		return new DefaultRateLimit(systemPublicMetrics);
	}
}
