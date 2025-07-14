package com.smc.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smc.Repository.UserRepo;
import com.smc.entities.User;
import com.smc.helper.AppConstants;
import com.smc.helper.Helper;
import com.smc.helper.ResourceNotFoundException;
import com.smc.services.EmailService;
import com.smc.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Helper helper;

    @Autowired
    private EmailService emailService;


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        // user id : have to generate
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        // password encode
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //set the user role
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        logger.info(user.getProvider().toString());

         String emailToken = UUID.randomUUID().toString();
         user.setEmailToken(emailToken);
         User savedUser = userRepo.save(user);
         String emailLink = helper.getLinkForEmailVarification(emailToken);
         emailService.sendEmail(savedUser.getEmail(),  "Verify Account : Smart  Contact Manager", 
        		         "<html>"
        				    + "<body style='font-family: Arial, sans-serif; color: #333; line-height: 1.6;'>"
        				    + "<p>Dear User,</p>"
        				    + "<p>Thank you for signing up with <strong>Smart Contact Manager</strong>! To complete your registration, please verify your email address by clicking the link below:</p>"
        				    + "<p style='text-align: center; margin: 20px 0;'>"
        				    + "<a href='" + emailLink + "' style='display: inline-block; padding: 10px 20px; font-size: 16px; color: #fff; background-color: #007bff; text-decoration: none; border-radius: 5px;'>Verify Email</a>"
        				    + "</p>"
        				    + "<p>If the button above does not work, you can copy and paste the following link into your browser:</p>"
        				    + "<p style='background-color: #f4f4f4; padding: 10px; border-radius: 5px;'>" + emailLink + "</p>"
        				    + "<p>Best Regards,</p>"
        				    + "<p><strong>Smart Contact Manager Team</strong></p>"
        				    + "</body>"
        				    + "</html>"

        		 
        		 );
         return savedUser;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User user2 = userRepo.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhone(user.getPhone());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVarified(user.isEmailVarified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());

        // save the user in database
        User save = userRepo.save(user2);
        return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String id) {
        User user2 = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepo.delete(user2);
    }

    @Override
    public boolean isUserExist(String userId) {
        User user2 = userRepo.findById(userId).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

	@Override
	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email).orElse(null);

	}

	@Override
	public boolean isEmailRegistered(String email) {
		// TODO Auto-generated method stub
		 return userRepo.existsByEmail(email);
	}

}
