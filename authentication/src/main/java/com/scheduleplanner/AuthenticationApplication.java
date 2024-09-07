package com.scheduleplanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication(scanBasePackages = {"com.scheduleplanner"})
@EnableAspectJAutoProxy
public class AuthenticationApplication {

	public static void main(String[] args)  {
		SpringApplication.run(AuthenticationApplication.class, args);
	}
}
