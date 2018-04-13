package org.mb.cloud.monitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: MonitorController
 * @Description: 监控
 * @author mb.wang  
 * @date 2018年1月19日 上午9:36:16
 * 
 */
@Controller
public class MonitorController {

	@RequestMapping("/")
	public String index() {
		return "forward:hystrix";
	}
}
