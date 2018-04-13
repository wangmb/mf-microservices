package org.mf.cloud.zuul.core.gray.ribbon;

import static org.mf.cloud.zuul.core.config.properties.ZuulExtProperties.PREFIX;

import org.mf.cloud.zuul.core.gray.ribbon.rule.AbstractDiscoveryEnabledRule;
import org.mf.cloud.zuul.core.gray.ribbon.rule.MetadataAwareRule;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: GrayRibbonDiscoveryRuleAutoConfiguration
 * @Description: 灰度发布自动配置
 * @author mb.wang  
 * @date 2018年1月9日 下午3:15:43
 * 
 */
@Configuration
@ConditionalOnClass(DiscoveryEnabledNIWSServerList.class)
@AutoConfigureBefore(RibbonClientConfiguration.class)
@ConditionalOnProperty(prefix=PREFIX,name="gray-released.enabled",havingValue="true",matchIfMissing=true)
@Slf4j
public class GrayRibbonDiscoveryRuleAutoConfiguration {

	public  GrayRibbonDiscoveryRuleAutoConfiguration() {
		log.info("----激活灰度发布----");
	}
	
	@Bean
	@ConditionalOnMissingBean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public AbstractDiscoveryEnabledRule metadataAwareRule() {
		return new MetadataAwareRule();
	}
}
