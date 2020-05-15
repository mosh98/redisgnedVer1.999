package com.userRed.redesigned.firebase;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Strings;

import lombok.AllArgsConstructor;
import lombok.val;
import lombok.extern.java.Log;

@Log
@AllArgsConstructor
@Component
public class FireBaseAuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
	AuthenticationManager authenticationManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain)
			throws ServletException,
			IOException {
		log.info("Firebase filtering...");

		final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (!Strings.isNullOrEmpty(authorization) && authorization.startsWith("Bearer ")) {
			final String idToken = authorization.replace("Bearer ", "");
			log.info("Bearer-token= " + idToken);
			val fireBaseAuthenticationToken = new FireBaseAuthenticationToken(idToken);
			val fireBaseAuthentication =
					(FireBaseAuthenticationToken) authenticationManager.authenticate(fireBaseAuthenticationToken);
			SecurityContextHolder.getContext()
					.setAuthentication(fireBaseAuthentication);
//			filterChain.doFilter(request, response);
		}
//		throw new SecurityException("Authentication failed, not bearer-token!");
//		log.info("...proceding to next filter in chain.");
		filterChain.doFilter(request, response);
	}

}
