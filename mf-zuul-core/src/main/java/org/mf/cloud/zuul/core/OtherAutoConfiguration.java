package org.mf.cloud.zuul.core;

import org.mf.cloud.zuul.core.zuul.fallback.ServiceZuulFallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @ClassName: OtherAutoConfiguration
 * @Description: 其他配置信息均配置到这里
 * @author mb.wang  
 * @date 2018年1月9日 下午2:55:52
 * 
 */
@Configuration
public class OtherAutoConfiguration {

	/**
	 * @Title: serviceZuulFallbackProvider
	 * @Description: 构建ServiceZuulFallbackProvider
	 * @author mb.wang  
	 * @date 2018年1月9日 下午2:56:38
	 * @return ServiceZuulFallbackProvider
	 */
	@Bean
	public ServiceZuulFallbackProvider serviceZuulFallbackProvider() {
		return new ServiceZuulFallbackProvider();
	}
	
    /**
     * @Title: corsFilter
     * @Description: 简单跨域就是GET，HEAD和POST请求，但是POST请求的"Content-Type"只能是application/x-www-form-urlencoded, multipart/form-data 或 text/plain
     * 反之，就是非简单跨域，此跨域有一个预检机制，说直白点，就是会发两次请求，一次OPTIONS请求，一次真正的请求
     * @author mb.wang  
     * @date 2018年1月8日 下午5:06:17
     * @return CorsFilter 
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);// 允许cookies跨域
        config.addAllowedOrigin("*");// #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
        config.addAllowedHeader("*");// #允许访问的头信息,*表示全部
        config.setMaxAge(18000L);// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
