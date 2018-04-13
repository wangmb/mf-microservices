package org.mf.cloud.zuul.core.dynamicroute;

import static org.mf.cloud.zuul.core.config.properties.ZuulExtProperties.PREFIX;

import org.mf.cloud.zuul.core.dynamicroute.discovery.DynamicRouteLocator;
import org.mf.cloud.zuul.core.dynamicroute.store.ZuulRouteStore;
import org.mf.cloud.zuul.core.dynamicroute.store.impl.CassandraZuulRouteStore;
import org.mf.cloud.zuul.core.dynamicroute.store.impl.DbZuulRouteStore;
import org.mf.cloud.zuul.core.dynamicroute.web.RouteChangeHandler;
import org.mf.cloud.zuul.core.dynamicroute.web.ZuulDynamicRouteMapping;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.cloud.netflix.zuul.web.ZuulController;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.cassandra.core.CassandraOperations;

import lombok.extern.slf4j.Slf4j;

@Configuration
@ConditionalOnProperty(prefix = PREFIX,name = "dynamic-route.enabled", havingValue = "true",matchIfMissing=false)
@Slf4j
public class DynamicRouteAutoConfiguration {
	public DynamicRouteAutoConfiguration() {
		log.info("----激活动态路由----");
	}

    @Bean
    public DiscoveryClientRouteLocator dynamicRouteLocator(ServerProperties serverProperties,
            ZuulProperties zuulProperties, DiscoveryClient discovery) {
        return new DynamicRouteLocator(serverProperties.getServletPath(), discovery, zuulProperties);
    }
    
    @Bean
    @Primary
    public ZuulHandlerMapping ZuulDynamicRouteMapping(RouteLocator routeLocator,ZuulController zuulController,ErrorController errorController) {
		ZuulDynamicRouteMapping mapping = new ZuulDynamicRouteMapping(routeLocator, zuulController);
        mapping.setErrorController(errorController);
    	return mapping;
    }
    
    @Bean
    @ConditionalOnBean(value= {ZuulHandlerMapping.class})
    public RouteChangeHandler routeChangeHandler() {
        return new RouteChangeHandler();
    }

    
    /**
     * @ClassName: CassandraZuulStoreAutoConfiguration
     * @Description: Cassandra配置
     * @author mb.wang  
     * @date 2018年1月3日 下午5:43:47
     * 
     */
    @Configuration
    @ConditionalOnProperty(prefix=PREFIX,value = "dynamic-route.repository", havingValue = "CASSANDRA")
    public static class CassandraZuulStoreAutoConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public ZuulRouteStore cassandraZuulRouteStore(CassandraOperations cassandraOperations) {
        		log.info("动态路由存储模式为：CASSANDRA");
            return new CassandraZuulRouteStore(cassandraOperations);
        }
    }

    /**
     * @ClassName: DbZuulStoreAutoConfiguration
     * @Description: DB配置。
     * @author mb.wang  
     * @date 2018年1月3日 下午5:43:47
     * 
     */
    @Configuration
    @ConditionalOnProperty(prefix=PREFIX,value = "dynamic-route.repository", havingValue = "DATABASE",matchIfMissing=true)
    @MapperScan(basePackages= {"com.tydic.bigdata.framework.zuul.core.dynamicroute.mapper"})
    public static class DbZuulStoreAutoConfiguration {

        @Bean
        @ConditionalOnMissingBean
        public ZuulRouteStore dbZuulRouteStore() {
    			log.info("动态路由存储模式为：DATABASE");

            return new DbZuulRouteStore();
        }
    }

}	