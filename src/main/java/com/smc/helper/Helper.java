package com.smc.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class Helper {
	public static String getEmailOfLoggedInUser(Authentication authentication) {

		// if logged with email
		if (authentication instanceof OAuth2AuthenticationToken) {

			var oAuth2Authentication = (OAuth2AuthenticationToken) authentication;
			var clientId = oAuth2Authentication.getAuthorizedClientRegistrationId();

			var oauth2User = (OAuth2User) authentication.getPrincipal();
			String username = "";

			// logged with google
			if (clientId.equalsIgnoreCase("google")) {

				System.out.println("Getting email from google");
				username = oauth2User.getAttribute("email").toString();
			}
			// logged with github
			else if (clientId.equalsIgnoreCase("github")) {
				System.out.println("Getting email from github");
				username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
						: oauth2User.getAttribute("login").toString() + "@gmail.com";
			}

			return username;
		} else {
			System.out.println("Gettion data from self");
			return authentication.getName();
		}

	}

	public String getLinkForEmailVarification(String emailToken) {

		String linkString = "http://localhost:9000/auth/verify-email?token=" + emailToken;

		return linkString;
	}

}
