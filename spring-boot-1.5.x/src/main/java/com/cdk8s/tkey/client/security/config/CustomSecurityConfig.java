package com.cdk8s.tkey.client.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * 注意：@EnableOAuth2Sso 需要放在这里
 */
@Configuration
@EnableOAuth2Sso
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${custom.variable.server-logout-url}")
	private String serverLogoutUrl;

	@Value("${custom.variable.server-logout-redirect-url}")
	private String serverLogoutRedirectUrl;

	//=================================================================================


	@Bean
	public LogoutFilter requestCasGlobalLogoutFilter() {
		String logoutUrl = serverLogoutUrl + "?redirect_uri=" + serverLogoutRedirectUrl;
		LogoutFilter logoutFilter = new LogoutFilter(logoutUrl, new SecurityContextLogoutHandler());
		logoutFilter.setLogoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"));
		return logoutFilter;
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();
		http.addFilterBefore(requestCasGlobalLogoutFilter(), LogoutFilter.class);

		http.authorizeRequests()
			.antMatchers("/", "/index", "/error").permitAll()
			.antMatchers("/login/**", "/logout/**", "/logoutSuccess").permitAll()
			.antMatchers("/css/**", "/js/**", "/fonts/**").permitAll()
			.anyRequest().authenticated();

		http.csrf().disable();
	}

}
