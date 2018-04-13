package org.mb.cloud.zikin.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class ZipkinTracingApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ZipkinTracingApplication.class,args);		
	}
}