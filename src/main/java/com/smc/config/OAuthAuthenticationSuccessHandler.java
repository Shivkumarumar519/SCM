package com.smc.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.smc.Repository.UserRepo;
import com.smc.entities.Providers;
import com.smc.entities.User;
import com.smc.helper.AppConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

	@Autowired
	private UserRepo repo;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		logger.info("OAuthAuthenticationSuccessHandler");

		var oauth2 = (OAuth2AuthenticationToken) authentication;

		String clientRegistrationId = oauth2.getAuthorizedClientRegistrationId();

		logger.info(clientRegistrationId);

		var oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
		oauthUser.getAttributes().forEach((key, value) -> {
			logger.info(key + " : " + value);
		});

		User user = new User();
		user.setUserId(UUID.randomUUID().toString());
		user.setRoleList(List.of(AppConstants.ROLE_USER));
		user.setEmailVarified(true);
		user.setEnabled(true);

		if (clientRegistrationId.equalsIgnoreCase("google")) {
			// google login

			user.setEmail(oauthUser.getAttribute("email").toString());
			user.setProfilePic(oauthUser.getAttribute("picture").toString());
			user.setName(oauthUser.getAttribute("name").toString());
			user.setProviderUserId(oauthUser.getName());
			user.setProvider(Providers.GOOGLE);
			user.setAbout("This account created with google");

		} else if (clientRegistrationId.equalsIgnoreCase("github")) {

			// github login

			String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
					: oauthUser.getAttribute("login").toString() + "@gmail.com";
			String picture = oauthUser.getAttribute("avatar_url").toString();
			String name = oauthUser.getAttribute("login").toString();
			String providerUserId = oauthUser.getName();
			user.setEmail(email);
			user.setProfilePic(picture);
			user.setName(name);
			user.setProviderUserId(providerUserId);
			user.setProvider(Providers.GITHUB);
			user.setAbout("This account created with github");

		} else if (clientRegistrationId.equalsIgnoreCase("linkedin")) {

			// linkedin
		} else {

			logger.info("OAuthAuthenticationSuccessHandler: Unknown provider");

		}

		/*
		 * DefaultOAuth2User principal = (DefaultOAuth2User)
		 * authentication.getPrincipal();
		 *
		 * // logger.info(principal.getName()); //
		 * principal.getAttributes().forEach((key,value)->{ //
		 * logger.info("{} => {}",key,value); // }); // //
		 * logger.info(principal.getAuthorities().toString()); // // //data database
		 * save: String email = principal.getAttribute("email").toString(); String name
		 * = principal.getAttribute("name").toString(); String picture =
		 * principal.getAttribute("picture").toString();
		 *
		 * //save in db User user = new User();
		 *
		 * user.setEmail(email); user.setName(name); user.setProfilePic(picture);
		 * user.setPassword("password"); user.setUserId(UUID.randomUUID().toString());
		 * user.setProvider(Providers.GOOGLE); user.setEnabled(true);
		 *
		 * user.setEmailVarified(true); user.setProviderUserId(user.getName());
		 * user.setRoleList(List.of(AppConstants.ROLE_USER));
		 * user.setAbout("This account is created with google");
		 */
		User user1 = repo.findByEmail(user.getEmail()).orElse(null);
		if (user1 == null) {
			repo.save(user);
		}

		new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
	}

}
