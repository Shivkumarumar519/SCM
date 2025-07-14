package com.smc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smc.entities.User;
import com.smc.forms.UserForm;
import com.smc.helper.Message;
import com.smc.helper.MessageType;
import com.smc.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping({ "/home", "/" })
    public String homePage(Model model) {
        System.out.println("Home Controller");
        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage() {
        System.out.println("About Controller");
        return "about";
    }

    @RequestMapping("/service")
    public String servicePage() {
        System.out.println("Service Controller");
        return "service";
    }

    @RequestMapping("/contact")
    public String contactPage() {
        System.out.println("Contact controller");
        return "contact";
    }

    @GetMapping("/login")
    public String loginPage() {
        System.out.println("login Controller");
        return new String("login");
    }

    @GetMapping("/register")
    public String registerPage(Model model) {

        UserForm userForm = new UserForm();

        model.addAttribute("userForm", userForm);
        System.out.println("Register Page");
        return "register";
    }

    // processing register
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult result,  HttpSession session) {
        System.out.println("registration");
        // fetch form data
        //System.out.println(userForm);
//
        if(result.hasErrors()) {
        	 System.out.println("Validation errors: " + result.getAllErrors());
        	return "register";
        }
//           User user = User.builder()
//                .name(userForm.getName())
//                .email(userForm.getEmail())
//                .password(userForm.getPassword())
//                .about(userForm.getAbout())
//                .phone(userForm.getPhone())
//                .profilePic("/image/progile.png")
//                .build();
        try {
            // Check if the email is already registered
            if (userService.isEmailRegistered(userForm.getEmail())) {
                Message message = Message.builder()
                    .content("Email is already registered. Please try logging in.")
                    .type(MessageType.red)
                    .build();
                session.setAttribute("message", message);
                return "redirect:/register";
            }


        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhone(userForm.getPhone());
        user.setEnabled(false);
        user.setProfilePic("https://cdn.pixabay.com/photo/2016/08/20/05/38/avatar-1606916_1280.png");
        User savedUser = userService.saveUser(user);
        System.out.println("saved user :");

        Message message = Message.builder().content("Registration Successfully done.").type(MessageType.green).build();

        session.setAttribute("message", message);

        return "redirect:/login";
    }catch (Exception e) {
		// TODO: handle exception
    	 System.out.println("Error during registration: " + e.getMessage());
         Message message = Message.builder()
             .content("Something went wrong. Please try again later.")
             .type(MessageType.red)
             .build();
         session.setAttribute("message", message);
         return "redirect:/register";
    }
    }
}
