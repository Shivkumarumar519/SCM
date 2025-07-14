package com.smc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.CacheControlHeadersWriter;

import com.smc.services.impl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {

//	//user create and login
//	@Bean
//	public UserDetailsService userDetailsService() {
//
//		UserDetails userDetails = User
//				.withDefaultPasswordEncoder()
//				.username("admin123")
//				.password("admin123")
//				.roles("ADMIN","USER")
//				.build();
//
//		var inMemoryUserDetailsService= new InMemoryUserDetailsManager(userDetails);
//		return inMemoryUserDetailsService;
//	}

	@Autowired
	private AuthFailtureHandler authFailtureHandler;

	@Autowired
	private SecurityCustomUserDetailService customUserDetailService;

	@Autowired
	private OAuthAuthenticationSuccessHandler authAuthenticationSuccessHandler;
	// configuration of authentication provider

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		// user detail service object

		daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
		// password encoder
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;

	}

	// page access for admin and normal users
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		// url configure
		httpSecurity.authorizeHttpRequests(authorize -> {
			// authorize.requestMatchers("/home","/register","/service","/contact","/about").permitAll();
			authorize.requestMatchers("/user/**").authenticated();
			authorize.anyRequest().permitAll();
		});

		httpSecurity.formLogin(
				//Customizer.withDefaults());
				formLogin ->{
			formLogin.loginPage("/login");

			formLogin.successForwardUrl("/user/profile");
			formLogin.usernameParameter("email");
			formLogin.passwordParameter("password");
			formLogin.loginProcessingUrl("/authenticate");
			formLogin.failureHandler(authFailtureHandler);

		});




		httpSecurity.csrf(AbstractHttpConfigurer::disable);

		httpSecurity.logout(logoutForm -> {
		    logoutForm.logoutUrl("/do-logout");
		    logoutForm.logoutSuccessUrl("/login?logout=true");
		    logoutForm.addLogoutHandler(new HeaderWriterLogoutHandler(new CacheControlHeadersWriter()));


		});

		//oauth configuration
		httpSecurity.oauth2Login(oauth->{
			oauth.loginPage("/login");
			oauth.successHandler(authAuthenticationSuccessHandler);
			});

		return httpSecurity.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

}
