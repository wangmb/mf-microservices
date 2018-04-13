package org.mf.cloud.zuul.core.gray.ribbon.context.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.mf.cloud.zuul.core.gray.ribbon.context.IRibbonFilterContext;

/**
 * @ClassName: DefaultRibbonFilterContext
 * @Description: 上下文操作默认接口（IRibbonFilterContext）实现类
 * @author mb.wang  
 * @date 2018年1月8日 下午2:08:03
 * 
 */
public class DefaultRibbonFilterContext implements IRibbonFilterContext {

    private final Map<String, String> attributes = new HashMap<>();
	
	@Override
	public IRibbonFilterContext add(String key, String value) {
		attributes.put(key, value);
		return this;
	}

	@Override
	public String get(String key) {
		return attributes.getOrDefault(key, "");
	}

	@Override
	public IRibbonFilterContext remove(String key) {
		attributes.remove(key);
		return this;
	}

	@Override
	public Map<String, String> getAttributes() {
		//返回只读map
		return Collections.unmodifiableMap(attributes);
	}

}
