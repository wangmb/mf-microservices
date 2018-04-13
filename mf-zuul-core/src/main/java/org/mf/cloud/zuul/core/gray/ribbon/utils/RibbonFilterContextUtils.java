package org.mf.cloud.zuul.core.gray.ribbon.utils;

import org.mf.cloud.zuul.core.gray.ribbon.context.IRibbonFilterContext;
import org.mf.cloud.zuul.core.gray.ribbon.context.impl.DefaultRibbonFilterContext;

/**
 * @ClassName: RibbonFilterContextUtils
 * @Description: RibbonFilterContext操作工具类，控制在当前线程内进行操作
 * @author mb.wang
 * @date 2018年1月8日 下午2:13:53
 * 
 */
public class RibbonFilterContextUtils {
	
	/**
	 * @Fields CONTEXT_THREAD_LOCAL : 备注：通过InheritableThreadLocal构建对象，可以使得子线程可以与父线程共同拥有父线程的线程内变量
	 * @author mb.wang  
	 * @date 2018年1月9日 下午2:47:01
	 */
	private static final ThreadLocal<IRibbonFilterContext> CONTEXT_THREAD_LOCAL = new InheritableThreadLocal<IRibbonFilterContext>() {
		@Override
		protected IRibbonFilterContext initialValue() {
			return new DefaultRibbonFilterContext();
		};
	};

	/**
	 * @Title: getCurrentRibbonFilterContext
	 * @Description: 获取当前线程对应的IRibbonFilterContext。
	 * @author mb.wang
	 * @date 2018年1月8日 下午2:24:59
	 * @return IRibbonFilterContext
	 */
	public static IRibbonFilterContext getCurrentRibbonFilterContext() {
		return CONTEXT_THREAD_LOCAL.get();
	}

	/**
	 * @Title: clearCurrentRibbonFilterContext @Description:
	 * 清除当前线程内RibbonFilterContext。用完后需要记得调用此方法 @author mb.wang @date 2018年1月8日
	 * 下午2:28:53 void 返回类型 @throws
	 */
	public static void clearCurrentRibbonFilterContext() {
		CONTEXT_THREAD_LOCAL.remove();
	}
}
