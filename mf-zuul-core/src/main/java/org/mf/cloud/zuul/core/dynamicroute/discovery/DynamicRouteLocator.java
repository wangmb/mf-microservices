package org.mf.cloud.zuul.core.dynamicroute.discovery;

import java.util.LinkedHashMap;

import org.mf.cloud.zuul.core.dynamicroute.store.ZuulRouteStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;

/**
 * @ClassName: DynamicRouteLocator
 * @Description: 扩展DiscoveryClientRouteLocator类，该类管理路由规则，默认是读取配置文件和注册中心的ServiceId自动生成路由规则。
 *  增加从redis/数据库/zk等中获取动态路由规则。暂不建议去扩展ZuulProxyConfiguration
 * @author mb.wang  
 * @date 2018年1月3日 下午2:47:27
 * 
 */
public class DynamicRouteLocator extends DiscoveryClientRouteLocator {
	static final Logger log = LoggerFactory.getLogger(DynamicRouteLocator.class);

    @Autowired
    private ZuulRouteStore zuulRouteStore;

    public DynamicRouteLocator(String servletPath, DiscoveryClient discovery, ZuulProperties properties) {
        super(servletPath, discovery, properties);
    }

    @Override
    protected LinkedHashMap<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routeMap = super.locateRoutes();
        
        //全量获取路由配置信息(默认定时刷新)
        zuulRouteStore.getAllRoutes().forEach(route -> routeMap.put(route.getPath(), route));

        return routeMap;
    }


}