package org.mf.cloud.zuul.core.ratelimit.filters;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mf.cloud.zuul.core.ratelimit.dto.RateLimitDTO;
import org.mf.cloud.zuul.core.ratelimit.support.IRateLimit;
import org.mf.cloud.zuul.core.ratelimit.support.IRateLimitStore;
import org.mf.cloud.zuul.core.zuul.AbstractZuulFilter;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UrlPathHelper;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: RateLimitPreFilter
 * @Description: 流量控制过滤器。缺少峰值削平之类的控制。
 * @author mb.wang  
 * @date 2018年1月10日 下午2:09:12
 * 
 */
@Slf4j
public class RateLimitPreFilter extends AbstractZuulFilter {
    public static final String LIMIT_HEADER = "X-RateLimit-Limit-";
    public static final String REQUEST_START_TIME = "rateLimitRequestStartTime";

	private final  IRateLimit rateLimit;
	private final IRateLimitStore rateLimitStore;
	private boolean enabled = false;
	
	public RateLimitPreFilter(IRateLimit rateLimit,IRateLimitStore rateLimitStore,RouteLocator routeLocator, UrlPathHelper urlPathHelper,boolean enabled) {
		super(routeLocator, urlPathHelper);
		this.rateLimit = rateLimit;
		this.rateLimitStore = rateLimitStore;
		this.enabled = enabled;
	}

	@Override
	public boolean shouldFilter() {
		final Route route = route();
		return enabled
				&&route!=null
				&& rateLimitStore.serviceExists(route.getId())
				&& rateLimit.shouldFilter(RequestContext.getCurrentContext().getRequest());
	}

	@Override
	public Object runExt() {
		final RequestContext ctx = RequestContext.getCurrentContext();
		final HttpServletRequest request = ctx.getRequest();
		final HttpServletResponse response = ctx.getResponse();
		final Route route = route();
		
		final RateLimitDTO rateLimitDTO = rateLimitStore.getRateLimitByServiceId(route.getId());
		String accessCode = rateLimit.getAccessCode(request, route,rateLimitDTO);
        accessCode = accessCode.replaceAll("[^A-Za-z0-9-.]", "_").replaceAll("__", "_");
		
		if(rateLimitDTO!=null && rateLimitDTO.getLimit()>0) {
			response.setHeader(LIMIT_HEADER+accessCode, String.valueOf(rateLimitDTO.getLimit()));
		}else {
			//不排除某种极端情况下，导致执行run方法时，微服务对应的限流配置信息从redis被误删除，误删除后，不能影响整个业务请求。
			log.warn("redis中不存在服务id为["+route.getId()+"]的限流配置信息。");
			return null;
		}
		
		if(rateLimitDTO.getLimit()>0&&rateLimitDTO.getRefreshIntervalSeconds()>0) {
			if(!rateLimitStore.calculateAndStoreRateLimit(rateLimitDTO,accessCode)) {
				 HttpStatus tooManyRequests = HttpStatus.TOO_MANY_REQUESTS;
	             ctx.setResponseStatusCode(tooManyRequests.value());
	             ctx.put("rateLimitExceeded", "true");
	             ctx.setSendZuulResponse(false);
	             ZuulException zuulException =
	                 new ZuulException(tooManyRequests.toString(), tooManyRequests.value(), null);
	             throw new ZuulRuntimeException(zuulException);
			}
		}else {
			//不限制
			if(log.isDebugEnabled()) {
				log.debug(accessCode+"不做限制,该微服务对应的限流配置信息为："+gson.toJson(rateLimitDTO));
			}
		}
		
		return null;
	}

	@Override
	public String filterType() {
		return PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return PRE_DECORATION_FILTER_ORDER;
	}

}
