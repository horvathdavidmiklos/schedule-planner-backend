package scheduleplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class ScheduleplannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScheduleplannerApplication.class, args);
	}

}
