package com.smc.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.smc.entities.User;
import com.smc.helper.Helper;
import com.smc.services.UserService;

@ControllerAdvice
public class RootController {


	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;


	@ModelAttribute
	public void addLoggedInUserInfo(Model model, Authentication authentication) {
		if(authentication==null) {
			return ;
		}
		String loggedInUser = Helper.getEmailOfLoggedInUser(authentication);
		logger.info("user logged in ", loggedInUser);
		// fetch data from db
		User byEmail = userService.getUserByEmail(loggedInUser);

		System.out.println(byEmail.getName());
		System.out.println(byEmail.getEmail());
		model.addAttribute("loggedInUser", byEmail);

	}

}
