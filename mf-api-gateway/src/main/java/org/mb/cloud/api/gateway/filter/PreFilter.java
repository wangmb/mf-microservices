package org.mb.cloud.api.gateway.filter;

import java.util.HashMap;

import org.mf.cloud.zuul.core.gray.ribbon.utils.RibbonFilterContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * Created by Charles on 2016/8/26.
 */
public class PreFilter extends ZuulFilter {
	private static final HashMap<String, String> TOKEN_LABEL_MAP = new HashMap<>();

	static {
		TOKEN_LABEL_MAP.put("emt", "EN,Male,Test");
		TOKEN_LABEL_MAP.put("eft", "EN,Female,Test");
		TOKEN_LABEL_MAP.put("cmt", "CN,Male,Test");
		TOKEN_LABEL_MAP.put("cft", "CN,Female,Test");
		TOKEN_LABEL_MAP.put("em", "EN,Male");
		TOKEN_LABEL_MAP.put("ef", "EN,Female");
		TOKEN_LABEL_MAP.put("cm", "CN,Male");
		TOKEN_LABEL_MAP.put("cf", "CN,Female");
	}

	private static final Logger logger = LoggerFactory.getLogger(PreFilter.class);

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		String token = ctx.getRequest().getParameter("token");// ctx.getRequest().getHeader(HttpHeaders.AUTHORIZATION);

		if (token != null) {
			String labels = TOKEN_LABEL_MAP.get(token);
			if (token.equals("ef")) {
				RibbonFilterContextUtils.clearCurrentRibbonFilterContext();
				RibbonFilterContextUtils.getCurrentRibbonFilterContext().add("version", "1.0.0");
			}
			logger.info("label: " + labels);
		}

		// HeaderHandlerInterceptorAdapter.initHystrixRequestContext(labels); //
		// zuul本身调用微服务
		// ctx.addZuulRequestHeader(HeaderHandlerInterceptorAdapter.HEADER_LABEL,
		// labels); // 传递给后续微服务

		return null;
	}
}
