package edc.projects.status.managing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ManagingApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(ManagingApplication.class, args);
	}
	
	 @Override
     protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
         return builder.sources(ManagingApplication.class);
     }
	 
}
