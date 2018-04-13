package org.mf.cloud.zuul.core.gray.ribbon.predicate;

import java.util.Iterator;
import java.util.Map;

import org.mf.cloud.zuul.core.gray.ribbon.context.IRibbonFilterContext;
import org.mf.cloud.zuul.core.gray.ribbon.utils.RibbonFilterContextUtils;

import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

/**
 * @ClassName: MetadataAwarePredicate
 * @Description: 默认（AbstractDiscoveryEnabledPredicate）实现，将默认注册到DiscoveryEnabledServer中的metadata与通过注册的属性进行匹配
 * @author mb.wang  
 * @date 2018年1月8日 下午2:33:25
 * 
 */
public class MetadataAwarePredicate extends AbstractDiscoveryEnabledPredicate {

	@Override
	boolean apply(DiscoveryEnabledServer server) {
		IRibbonFilterContext context = RibbonFilterContextUtils.getCurrentRibbonFilterContext();
        Map<String, String> attributes = context.getAttributes();
        Map<String, String> metadata = server.getInstanceInfo().getMetadata();
        
        boolean isOk = true;
        
        Iterator<String> keys = attributes.keySet().iterator();
        while(keys.hasNext()) {
        		String key = keys.next();
        		if(!(metadata.containsKey(key)&&(metadata.get(key)).equals(attributes.get(key)))) {
            		isOk = false;
            		break;
        		}
        }

        return isOk;
	}

}
