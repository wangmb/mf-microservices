package org.mf.cloud.zuul.core.dynamicroute.store;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

/**
 * @ClassName: ZuulRouteStore
 * @Description: 路由规则操作接口定义
 * @author mb.wang  
 * @date 2018年1月3日 下午2:56:31
 * 
 */
public interface ZuulRouteStore {

    /**
     * @Title: getAllRoutes
     * @Description: 获取所有路由规则信息
     * @author mb.wang  
     * @date 2018年1月3日 下午2:57:00
     * @return List<ZuulProperties.ZuulRoute>  
     */
    List<ZuulProperties.ZuulRoute> getAllRoutes();

    /**
     * @Title: onRoutesChange
     * @Description: 改变路由规则
     * @author mb.wang  
     * @date 2018年1月3日 下午2:57:32
     * @param handleFunction 
     * @return void  
     */
    void onRoutesChange(Consumer<List<ZuulProperties.ZuulRoute>> handleFunction);
}