package org.mf.cloud.zuul.core.gray.ribbon.rule;

import org.mf.cloud.zuul.core.gray.ribbon.predicate.AbstractDiscoveryEnabledPredicate;
import org.mf.cloud.zuul.core.gray.ribbon.predicate.MetadataAwarePredicate;

/**
 * @ClassName: MetadataAwareRule
 * @Description: metadata {@link DiscoveryEnabledRule} implementation.
 * @author mb.wang  
 * @date 2018年1月8日 下午2:41:48
 * 
 */
public class MetadataAwareRule extends AbstractDiscoveryEnabledRule {

	public MetadataAwareRule() {
		this(new MetadataAwarePredicate());
	}
	
	public MetadataAwareRule(AbstractDiscoveryEnabledPredicate discoveryEnabledPredicate) {
		super(discoveryEnabledPredicate);
	}

}
