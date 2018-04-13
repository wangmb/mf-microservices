CREATE TABLE `api_gateway_route` (
  `id` varchar(50) NOT NULL COMMENT '服务',
  `path` varchar(255) NOT NULL COMMENT '路由规则（需满足Ant path匹配规则）',
  `service_id` varchar(50) DEFAULT NULL COMMENT '服务ID，注册到服务中心的服务编码。与服务地址不能同时存在。',
  `url` varchar(255) DEFAULT NULL COMMENT '服务地址。与服务ID不能同时存在。',
  `retryable` tinyint(1) DEFAULT '1' COMMENT '标记该Route是否可重试的（如果支持的话）。 重试需要服务ID和ribbon。',
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `strip_prefix` int(11) DEFAULT NULL,
  `server_name` varchar(255) DEFAULT NULL COMMENT '服务名称（以路由规则无关）。',
  `create_date` datetime NOT NULL,
  `state` varchar(3) NOT NULL,
  `update_date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8