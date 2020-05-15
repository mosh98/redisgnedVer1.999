//package com.userRed.redesigned.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.userRed.redesigned.repository.UsersRepository;
//import com.userRed.redesigned.service.MyUserDetailsService;
//
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableWebSecurity
//@EnableJpaRepositories(basePackageClasses = UsersRepository.class)
//@Configuration
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	private MyUserDetailsService userDetailsService;
//
//	// Creates bean for authenticationManager later used for authentication of
//	// authentication object(token).
//	@Bean
//	public AuthenticationManager AuthenticationManager() throws Exception {
//		return super.authenticationManagerBean();
//	}
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//		auth.userDetailsService(userDetailsService)
//				.passwordEncoder(getPasswordEncoder());
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//		http.csrf()
//				.disable()
//				// dont authenticate this particular request
//				.authorizeRequests()
//				.antMatchers("/authenticate",
//						"/registerWithMail",
//						"dog/register",
//						"/addNewDog",
//						"/register",
//						"/getMyDogs",
//						"/dogpark/**",
//						"/dog/**",
//						"/user/**",
//						"/wastebin/**",
//						"/login/**")
//				.permitAll()
//				.anyRequest()
//				.authenticated();
//	}
//
//	private PasswordEncoder getPasswordEncoder() {
//		return new PasswordEncoder() {
//			@Override
//			public String encode(CharSequence charSequence) {
//				return charSequence.toString();
//			}
//
//			@Override
//			public boolean matches(	CharSequence charSequence,
//									String s) {
//				return true;
//			}
//		};
//	}
//}
