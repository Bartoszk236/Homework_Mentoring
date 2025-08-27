package com.MultiTenantUserManagementSystem;

import com.MultiTenantUserManagementSystem.utils.RateLimitProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(RateLimitProperties.class)
public class MultiTenantUserManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiTenantUserManagementSystemApplication.class, args);
	}

}
