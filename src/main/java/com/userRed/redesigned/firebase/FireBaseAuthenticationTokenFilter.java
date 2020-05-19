package com.userRed.redesigned.firebase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
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
	private final AuthenticationManager authenticationManager;

	private final ArrayList<String> excludeUrlPatterns = new ArrayList<String>(Arrays.asList("/user/register"));
	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return excludeUrlPatterns.stream()
				.anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
	}

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
			log.info("...found token...");
			val fireBaseAuthenticationToken = (FireBaseAuthenticationToken) authenticationManager
					.authenticate(new FireBaseAuthenticationToken(idToken));
			SecurityContextHolder.getContext()
					.setAuthentication(fireBaseAuthenticationToken);
			log.info("...done!");
			filterChain.doFilter(request, response);
		} else {
			log.warning("...failed!");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed, invalid token.");
		}
	}
}