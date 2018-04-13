package org.mf.cloud.zuul.core.dynamicroute.store.impl;

import java.util.List;
import java.util.function.Consumer;

import org.mf.cloud.zuul.core.dynamicroute.mapper.ApiGatewayRouteMapper;
import org.mf.cloud.zuul.core.dynamicroute.store.ZuulRouteStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: DbZuulRouteStore
 * @Description: 从数据库中获取路由规则。
 * @author mb.wang  
 * @date 2018年1月3日 上午11:27:41
 * 
 */
@Slf4j
public class DbZuulRouteStore implements ZuulRouteStore {
	@Autowired
	private ApiGatewayRouteMapper apiGatewayRouteMapper;

	@Override
	public List<ZuulProperties.ZuulRoute> getAllRoutes() {
		List<ZuulProperties.ZuulRoute> routes = this.apiGatewayRouteMapper.queryAll();
		log.info("query route info: {}",routes);
		
		return routes;
	}

	@Override
	public void onRoutesChange(Consumer<List<ZuulRoute>> handleFunction) {
		handleFunction.accept(getAllRoutes());
	}

}
