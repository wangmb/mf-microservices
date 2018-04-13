package org.mf.cloud.zuul.core.zuul;

import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.UrlPathHelper;

import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import lombok.RequiredArgsConstructor;

/**
 * @ClassName: AbstractZuulFilter
 * @Description: ZuulFilter扩展，在run方法中增加try...catch。把发生的运行时异常统一封装为ZuulRuntimeException
 *               若不这么做，那么post产生的异常将无法捕获，浏览器界面表现为空白。
 * @author mb.wang
 * @date 2018年1月9日 上午10:29:46
 * 
 */
@RequiredArgsConstructor
public abstract class AbstractZuulFilter extends ZuulFilter {
	private final RouteLocator routeLocator;
	private final UrlPathHelper urlPathHelper;
	protected static final Gson gson = new Gson();

	public abstract Object runExt();

	public int getStatusCode(Exception e) {
		return HttpStatus.BAD_REQUEST.value();
	};

	@Override
	public Object run() {
		try {
			return runExt();
		} catch (Exception e) {// 统一异常处理，把所有异常封装为ZuulRuntimeException
			throw new ZuulRuntimeException(new ZuulException(e, getStatusCode(e), e.getMessage()));
		}
	}

	/**
	 * @Title: route
	 * @Description: 获取当前请求服务对应的服务路由
	 * @author mb.wang  
	 * @date 2018年1月10日 上午11:58:51
	 * @return Route  
	 */
	public Route route() {
		String requestURI = urlPathHelper.getPathWithinApplication(RequestContext.getCurrentContext().getRequest());
		return routeLocator.getMatchingRoute(requestURI);
	}
}
