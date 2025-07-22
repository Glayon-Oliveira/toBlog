package com.lmlasmo.toblog.r2dbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.reactive.TransactionalOperator;

import io.r2dbc.spi.ConnectionFactory;

@Configuration
@EnableTransactionManagement
public class R2dbcConfig {

	@Bean
	public ReactiveTransactionManager reactiveTransactionManager(ConnectionFactory factory) {
		return new R2dbcTransactionManager(factory);
	}

	@Bean
	public TransactionalOperator transactionalOperator(ReactiveTransactionManager manager) {
		return TransactionalOperator.create(manager);
	}

}
