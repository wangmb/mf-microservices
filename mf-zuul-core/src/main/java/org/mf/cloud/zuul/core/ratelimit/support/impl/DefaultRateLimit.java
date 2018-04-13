package org.mf.cloud.zuul.core.ratelimit.support.impl;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.X_FORWARDED_FOR_HEADER;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.StringJoiner;

import javax.servlet.http.HttpServletRequest;

import org.mf.cloud.zuul.core.ratelimit.RateLimitType;
import org.mf.cloud.zuul.core.ratelimit.dto.RateLimitDTO;
import org.mf.cloud.zuul.core.ratelimit.support.IRateLimit;
import org.springframework.boot.actuate.endpoint.SystemPublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.cloud.netflix.zuul.filters.Route;

import lombok.RequiredArgsConstructor;

/**
 * @ClassName: DefaultRateLimit
 * @Description: 默认流量限制接口实现
 * @author mb.wang
 * @date 2018年1月5日 下午5:51:21
 * 
 */
@RequiredArgsConstructor
public class DefaultRateLimit implements IRateLimit {
	
	private final SystemPublicMetrics systemPublicMetrics;
	/**
	 * @Fields ANONYMOUS_USER : 匿名用户
	 * @author mb.wang
	 * @date 2018年1月10日 上午11:29:05
	 */
	private static final String ANONYMOUS_USER = "anonymous";

	/**
	 * @Title: freeMemory
	 * @Description: 检查当前节点空闲内存。空闲内存过低，则开启限流控制（目前是对微服务进行限流）。
	 * @author mb.wang  
	 * @date 2018年1月10日 下午5:20:24
	 * @return boolean 
	 */
	private boolean freeMemory() {
		Collection<Metric<?>> metrics = systemPublicMetrics.metrics();
		Optional<Metric<?>> freeMemoryMetric = metrics.stream().filter(t -> "mem.free".equals(t.getName())).findFirst();
		// 如果不存在这个指标，稳妥起见，返回true，开启限流
		if (!freeMemoryMetric.isPresent()) {
			return true;
		}
		long freeMemory = freeMemoryMetric.get().getValue().longValue();
		// 如果可用内存小于1000000KB，开启流控
		return freeMemory < 1000000L;
	}
	
	@Override
	public boolean shouldFilter(HttpServletRequest request) {
		return true;
	}

	@Override
	public String getAccessCode(HttpServletRequest request, Route route, RateLimitDTO rateLimitDTO) {
		final StringJoiner joiner = new StringJoiner(":");
		String serverId = null;
		if (route != null) {
			serverId = route.getId();
			joiner.add(serverId);
		}

		if (rateLimitDTO != null && route != null) {
			HashSet<RateLimitType> typeSet = rateLimitDTO.getTypes();
			if(typeSet.contains(RateLimitType.URL)) {
				joiner.add(route.getPath());
			}
			if(typeSet.contains(RateLimitType.ORIGIN)) {
				joiner.add(getRemoteAddress(request));
			}
			if(typeSet.contains(RateLimitType.USER)) {
				joiner.add(request.getRemoteUser() != null ? request.getRemoteUser() : ANONYMOUS_USER);
			}
		}

		return joiner.toString();
	}

	private String getRemoteAddress(final HttpServletRequest request) {
		String xForwardedFor = request.getHeader(X_FORWARDED_FOR_HEADER);
		if (xForwardedFor != null) {
			return xForwardedFor;
		}
		return request.getRemoteAddr();
	}

}
