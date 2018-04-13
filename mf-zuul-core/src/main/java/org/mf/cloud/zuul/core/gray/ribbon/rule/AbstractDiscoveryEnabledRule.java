package org.mf.cloud.zuul.core.gray.ribbon.rule;

import org.mf.cloud.zuul.core.gray.ribbon.predicate.AbstractDiscoveryEnabledPredicate;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.CompositePredicate;
import com.netflix.loadbalancer.PredicateBasedRule;

/**
 * @ClassName: AbstractDiscoveryEnabledRule
 * @Description: IRule。
 * @author mb.wang  
 * @date 2018年1月8日 下午2:43:33
 * 
 */
public class AbstractDiscoveryEnabledRule extends PredicateBasedRule {

	private CompositePredicate compositePredicate;
	
	public AbstractDiscoveryEnabledRule(AbstractDiscoveryEnabledPredicate discoveryEnabledPredicate) {
		
		this.compositePredicate = CompositePredicate.withPredicates(discoveryEnabledPredicate).build();
	}
	
	@Override
	public AbstractServerPredicate getPredicate() {
		return compositePredicate;
	}

}
