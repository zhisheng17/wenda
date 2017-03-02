package com.nowcoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication	//该注解等价于已默认属性使用@Configuration, @EnableAutoConfiguration和@ComponentScan
public class WendaApplication extends SpringBootServletInitializer
{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
	{
		return builder.sources(WendaApplication.class);
	}

	public static void main(String[] args)
	{
		SpringApplication.run(WendaApplication.class, args);
	}
}
