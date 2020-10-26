package com.ss;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.ss.pj.common.cache.DefaultCache;

@SpringBootApplication
@MapperScan("com.ss.pj.**.dao")
public class Application implements CommandLineRunner {
	@Autowired
	private ApplicationContext ctx;
	
	@Autowired
	private DefaultCache cache;
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println(ctx);
		System.out.println(cache);
		System.out.println(dataSource.getConnection());
		System.out.println(dataSource.getClass());
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
