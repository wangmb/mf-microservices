package org.mf.cloud.zuul.core.dynamicroute.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

/**
 * @ClassName: ApiGatewayRouteMapper
 * @Description: 从api_gateway_route数据库表中获取动态配置的网关信息
 * @author mb.wang  
 * @date 2018年1月9日 下午2:57:43
 * 
 */
@Mapper
public interface ApiGatewayRouteMapper {
	
	/**
	 * @Title: queryAll
	 * @Description: 查询所有路由信息
	 * @author mb.wang  
	 * @date 2018年1月9日 下午3:22:16
	 * @return List<ZuulProperties.ZuulRoute> 
	 */
	@Results(id="zuulRouteResult",value= {
			@Result(property="id",column="id",id=true),
			@Result(property="path",column="path"),
			@Result(property="serviceId",column="service_id"),
			@Result(property="url",column="url"),
			@Result(property="retryable",column="retryable",javaType=Boolean.class),
			@Result(property="stripPrefix",column="strip_prefix",javaType=Boolean.class)
	})
	@Select("SELECT id,`path`,service_id,url,retryable,strip_prefix FROM api_gateway_route WHERE state='10A'")
	List<ZuulProperties.ZuulRoute> queryAll();
}
