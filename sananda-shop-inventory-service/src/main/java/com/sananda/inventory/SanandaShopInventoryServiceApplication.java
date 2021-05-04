package com.sananda.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaAuditing
public class SanandaShopInventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SanandaShopInventoryServiceApplication.class, args);
	}

}
