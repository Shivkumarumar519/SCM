package com.smc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smc.Repository.UserRepo;
import com.smc.entities.User;
import com.smc.helper.Message;
import com.smc.helper.MessageType;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

	//verify email
	@Autowired
	private UserRepo userRepo;

	@GetMapping("/verify-email")
	public String verifyEmail(@RequestParam("token") String token ,HttpSession session) {
		User user = userRepo.findByEmailToken(token).orElse(null);

		if(user!=null)
		{
			if(user.getEmailToken().equals(token))
			{
				user.setEmailVarified(true);
				user.setEnabled(true);
				userRepo.save(user);
				session.setAttribute("message", Message.builder()
						.type(MessageType.green)
						.content("Email is verified ! Please login now.")
						.build());
				return "redirect:/login";
			}
			session.setAttribute("message", Message.builder()
					.type(MessageType.red)
					.content("Email is not verified ! Please try again.")
					.build());
			return "redirect:/login";

		}
		session.setAttribute("message", Message.builder()
				.type(MessageType.red)
				.content("Email is not verified ! Please try again.")
				.build());
		return "redirect:/login";
	}
}
