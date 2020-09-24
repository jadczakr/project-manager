package edc.projects.status.managing;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
@EnableWebSecurity
public class SpringSecurityWebAppConfig extends WebSecurityConfigurerAdapter{
	
	@SuppressWarnings("deprecation")
	@Bean
	@Override
	public UserDetailsService userDetailsService() {

		List<UserDetails> users = new ArrayList<>();
		
		users.add(User.withDefaultPasswordEncoder().username("jadczakr").password("jadczakr1").roles("USER").build());
	    users.add(User.withDefaultPasswordEncoder().username("barylas").password("barylas1").roles("USER").build());
		users.add(User.withDefaultPasswordEncoder().username("miskiewicza").password("miskiewicza1").roles("USER").build());
		users.add(User.withDefaultPasswordEncoder().username("miskiewiczma").password("miskiewiczma1").roles("USER").build());
		users.add(User.withDefaultPasswordEncoder().username("wlochowiczp").password("wlochowiczp1").roles("USER").build());
		users.add(User.withDefaultPasswordEncoder().username("bagnowskij").password("bagnowskij1").roles("USER").build());
		users.add(User.withDefaultPasswordEncoder().username("kaletad").password("kaletad1").roles("USER").build());
		users.add(User.withDefaultPasswordEncoder().username("szczepanskis").password("szczepanskis1").roles("USER").build());
		users.add(User.withDefaultPasswordEncoder().username("kaczorowskil").password("kaczorowskil1").roles("USER").build());
		users.add(User.withDefaultPasswordEncoder().username("zielonkaw").password("zielonkaw1").roles("USER").build());
		
		
		return new InMemoryUserDetailsManager(users);
	
	}

	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/resources/**").permitAll()
			.antMatchers("/*").hasAnyRole("USER","ADMIN")
			.and()
			.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login-transfer")
				.permitAll()
			.and()
			.logout().permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/access-denied");
	}

}
	