package com.userRed.redesigned.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.userRed.redesigned.firebase.FireBaseAuthenticationProvider;
import com.userRed.redesigned.firebase.FireBaseAuthenticationTokenFilter;
import com.userRed.redesigned.service.UserService;

import lombok.extern.java.Log;

@Log
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	@Autowired
	UserService userDetailsService;

	// add AuthenticationProvider to list in AutenticationManager
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(new FireBaseAuthenticationProvider(userDetailsService));
	}

	// Creates bean for authenticationManager later used for authentication of
	// authentication object(token).
	@Bean
	public AuthenticationManager AuthenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("Configuring HTTP security...");
		http.csrf()
				.disable() // Disable csrf if server not for web services.
//				.sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//				.and()
//				.addFilterBefore(new CustomAuthenticationFilter(userDetailsService, authenticationManager()),
//						BasicAuthenticationFilter.class)
//				.addFilterAfter(new CustomAuthenticationTokenFilter(userDetailsService, authenticationManager()),
//						CustomAuthenticationFilter.class)
				.addFilterBefore(new FireBaseAuthenticationTokenFilter(authenticationManager()),
						BasicAuthenticationFilter.class)

				// addFilterAfter(new CustomAuthenticationTokenFilter(),
				// CustomAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers("/**") // .hasRole("ADMIN")
				.permitAll() // .hasRole("USER")
//				.hasAuthority("ADdMIN") //.denyAll()
				// .hasAuthority("ADFGDFG")
//				.permitAll()
				.anyRequest()
				.authenticated()
//				.and()
//				// make sure we use stateless session; session won't be used to
//				// store user's state.
//				.exceptionHandling()
//				.authenticationEntryPoint(serverAuthenticationEntryPoint) // HTTP 401
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//				.and()
//				.httpBasic();
		log.info("...done!");
	}

}
