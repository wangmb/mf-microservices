package org.mf.cloud.zuul.core.dynamicroute.web;

import org.mf.cloud.zuul.core.dynamicroute.store.ZuulRouteStore;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;

@Data
public class RouteChangeHandler implements InitializingBean {
    @Autowired
    private ZuulRouteStore zuulRouteStore;
    @Autowired
    private ZuulDynamicRouteMapping zuulDynamicMapping;
    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.zuulRouteStore == null) {
            throw new IllegalStateException("route store is null");
        }
        this.zuulRouteStore.onRoutesChange(routes -> {
            zuulDynamicMapping.setDirty(true);
        });
    }
}