package org.mf.cloud.zuul.core.config.properties;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: ZuulExtProperties
 * @Description: 扩展属性，定义扩展组件配置属性信息
 * @author mb.wang  
 * @date 2018年1月9日 下午2:51:03
 * 
 */
@Data
@Validated
@NoArgsConstructor
@ConfigurationProperties(ZuulExtProperties.PREFIX)
public class ZuulExtProperties {
    public static final String PREFIX = "zuul.mf";
    
    private DynamicRoute dynamicRoute;
    
    private GrayReleased grayReleased;
    
    private RateLimit rateLimit;
    
    /**
     * @ClassName: RateLimit
     * @Description: 微服务限流控制
     * @author mb.wang  
     * @date 2018年1月10日 下午4:43:40
     * 
     */
    @Data
    public static class RateLimit{
		private boolean enabled;
		@NotNull
		private String cachePrefix;
    }
    
    /**
     * @ClassName: GrayReleased
     * @Description: 灰度发布
     * @author mb.wang  
     * @date 2018年1月8日 下午3:08:59
     * 
     */
    @Data
    @NoArgsConstructor
    public static class GrayReleased{
    		private boolean enabled;
    		
    }
    
    /**
     * @ClassName: DynamicRoute
     * @Description: 动态路由
     * @author mb.wang  
     * @date 2018年1月8日 下午3:09:14
     * 
     */
    @Data
    @NoArgsConstructor
    public static class DynamicRoute{
    		/**
    		 * @Fields repository : DATABASE/CASSANDRA
    		 * @author mb.wang  
    		 * @date 2018年1月8日 下午3:32:38
    		 */
    		@NotNull
    		private Repository repository = Repository.DATABASE;
    		private boolean enabled;
    		
    		public enum Repository {
    	        /**
    	         * @Fields DATABASE : DATABASE
    	         * @author mb.wang  
    	         * @date 2018年1月9日 下午2:47:35
    	         */
    			DATABASE,
    	        /**
    	         * @Fields CASSANDRA : CASSANDRA
    	         * @author mb.wang  
    	         * @date 2018年1月9日 下午2:47:38
    	         */
    	        CASSANDRA
    	    }
    }
}
