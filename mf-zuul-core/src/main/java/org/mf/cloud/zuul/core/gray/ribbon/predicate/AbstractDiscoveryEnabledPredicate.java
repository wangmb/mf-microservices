package org.mf.cloud.zuul.core.gray.ribbon.predicate;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.PredicateKey;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

/**
 * @ClassName: AbstractDiscoveryEnabledPredicate
 * @Description: 扩展AbstractServerPredicate，通过控制apply方法返回结果来实现微服务的灰度发布
 * @author mb.wang  
 * @date 2018年1月9日 下午3:16:18
 * 
 */
public abstract class AbstractDiscoveryEnabledPredicate extends AbstractServerPredicate {

	@Override
	public boolean apply(PredicateKey predicateKey) {
		return predicateKey!=null 
				&&predicateKey.getServer() instanceof DiscoveryEnabledServer
				&&apply((DiscoveryEnabledServer)predicateKey.getServer());
	}
	
	/**
	 * @Title: apply
	 * @Description: 服务启用，则返回true，不启用则返回false。实现微服务的灰度发布。
	 * @author mb.wang  
	 * @date 2018年1月8日 上午11:55:11
	 * @param server
	 * @return boolean  
	 */
	abstract boolean apply(DiscoveryEnabledServer server);

}
