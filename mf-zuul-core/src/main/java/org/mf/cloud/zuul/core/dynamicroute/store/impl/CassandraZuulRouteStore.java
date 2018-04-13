package org.mf.cloud.zuul.core.dynamicroute.store.impl;

import java.util.List;
import java.util.function.Consumer;

import org.mf.cloud.zuul.core.dynamicroute.store.ZuulRouteStore;
import org.springframework.cassandra.core.RowMapper;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.data.cassandra.core.CassandraOperations;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.exceptions.DriverException;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: CassandraZuulRouteStore
 * @Description: 从Cassandra中获取路由规则。
 * @author mb.wang  
 * @date 2018年1月3日 下午5:38:45
 * 
 */
@Slf4j
public class CassandraZuulRouteStore implements ZuulRouteStore {
	private static final ZuulRouteRowMapper ZUUL_ROUTE_MAPPER = new ZuulRouteRowMapper();

    /**
     * @Fields DEFAULT_TABLE_NAME : 默认表名
     * @author mb.wang  
     * @date 2018年1月3日 下午5:33:13
     */
    private static final String DEFAULT_TABLE_NAME = "api_gateway_route";
    
    private final String keyspace;

    private final String table;
	
    /**
     * @Fields cassandraOperations : Cassandra
     * @author mb.wang  
     * @date 2018年1月3日 下午5:33:35
     */
    private final CassandraOperations cassandraOperations;
	
	public CassandraZuulRouteStore(CassandraOperations cassandraOperations) {
		this(cassandraOperations, null, DEFAULT_TABLE_NAME);
	}
	
	public CassandraZuulRouteStore(CassandraOperations cassandraOperations,String keyspace,String table) {
		this.keyspace = keyspace;
		this.cassandraOperations = cassandraOperations;
		this.table = table;
	}
	
	@Override
	public List<ZuulProperties.ZuulRoute> getAllRoutes() {
		final Select select = QueryBuilder.select().from(keyspace, table);
		
		return this.cassandraOperations.query(select, ZUUL_ROUTE_MAPPER);
	}
	
	private static class ZuulRouteRowMapper implements RowMapper<ZuulRoute> {

		@Override
		public ZuulProperties.ZuulRoute mapRow(Row row, int rowNum) throws DriverException {
			
			return new ZuulRoute(row.getString("id"),
					row.getString("path"), 
					row.getString("serviceId"), 
					row.getString("url"), 
					row.getBool("stripPrefix"),
					row.getBool("retryable"), null);
		}
		
	}

	@Override
	public void onRoutesChange(Consumer<List<ZuulRoute>> handleFunction) {
		handleFunction.accept(getAllRoutes());
		
	}

}
