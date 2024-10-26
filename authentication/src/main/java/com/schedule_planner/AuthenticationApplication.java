package com.schedule_planner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {"com.schedule_planner"})
@EnableAspectJAutoProxy
public class AuthenticationApplication {

	public static void main(String[] args)  {
		SpringApplication.run(AuthenticationApplication.class, args);
	}
}
