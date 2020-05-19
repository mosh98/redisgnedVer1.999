package com.userRed.redesigned.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userDetailsService;

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
				.authorizeRequests()
				.antMatchers("/**")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.addFilterBefore(new FireBaseAuthenticationTokenFilter(authenticationManager()),
						BasicAuthenticationFilter.class)
//				.exceptionHandling()
//				.authenticationEntryPoint(serverAuthenticationEntryPoint) // HTTP 401
//				.and()
				// make sure we use stateless session; session won't be used to
				// store user's state.
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		log.info("...done!");
	}

}