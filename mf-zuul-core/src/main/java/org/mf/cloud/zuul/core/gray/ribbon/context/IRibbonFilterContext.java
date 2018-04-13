package org.mf.cloud.zuul.core.gray.ribbon.context;

import java.util.Map;


/**
 * @ClassName: IRibbonFilterContext
 * @Description: RibbonFilterContext属性操作
 * @author mb.wang  
 * @date 2018年1月8日 上午11:42:10
 * 
 */
public interface IRibbonFilterContext {

    /**
     * @Title: add
     * @Description: 添加(RibbonFilterContext)上下文属性
     * @author mb.wang  
     * @date 2018年1月8日 上午11:42:20
     * @param key 属性key
     * @param value  属性value
     * @return IRibbonFilterContext 
     */
	IRibbonFilterContext add(String key, String value);

    /**
     * @Title: get
     * @Description: 根据传入key获取上下文属性值
     * @author mb.wang  
     * @date 2018年1月8日 上午11:43:35
     * @param key 属性key
     * @return String 
     */
    String get(String key);

    /**
     * @Title: remove
     * @Description: 根据属性key删除对应的上下文属性
     * @author mb.wang  
     * @date 2018年1月8日 上午11:44:55
     * @param key  属性key
     * @return IRibbonFilterContext 
     */
    IRibbonFilterContext remove(String key);

    /**
     * @Title: getAttributes
     * @Description: 获取所以属性集合，返回一个map对象
     * @author mb.wang  
     * @date 2018年1月8日 上午11:46:00
     * @return Map<String,String>  
     */
    Map<String, String> getAttributes();
}
