package org.mf.cloud.zuul.core.dynamicroute.web;

import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.web.ZuulController;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;

import io.netty.util.internal.ConcurrentSet;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: ZuulDynamicRouteMapping
 * @Description: 扩展ZuulHandlerMapping，重新registerHandler方法，实现为给定的URL路径注册指定的处理程序。
 * @author mb.wang  
 * @date 2018年1月9日 下午3:12:35
 * 
 */
@Slf4j
public class ZuulDynamicRouteMapping extends ZuulHandlerMapping {
    private final Set<String> registeredUrls = new ConcurrentSet<>();

    public ZuulDynamicRouteMapping(RouteLocator routeLocator, ZuulController zuul) {
        super(routeLocator, zuul);
    }

    @Override
    protected void registerHandler(String urlPath, Object handler) throws BeansException, IllegalStateException {
        // 当url已经注册时，直接返回
    		log.info("注册的urlPath:"+urlPath);
        if (registeredUrls.contains(urlPath)) {
            return;
        }

        registeredUrls.add(urlPath);

        super.registerHandler(urlPath, handler);
    }

}