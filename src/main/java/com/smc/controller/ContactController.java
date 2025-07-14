package com.smc.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.smc.entities.Contact;
import com.smc.entities.User;
import com.smc.forms.ContactForm;
import com.smc.forms.ContactSearchForm;
import com.smc.helper.AppConstants;
import com.smc.helper.Helper;
import com.smc.helper.Message;
import com.smc.helper.MessageType;
import com.smc.services.ContactService;
import com.smc.services.UserService;
import com.smc.services.ImageService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.experimental.var;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);

	@Autowired
	private ContactService contactService;
	
	@Autowired
	private ImageService imageService;

	@Autowired
	private UserService userService;

	@RequestMapping("/add")
	public String addContactView(Model model) {
		ContactForm contactForm = new ContactForm();
		model.addAttribute("contactForm", contactForm);
		return "user/add_contact";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
			Authentication authentication ,HttpSession session) {
		// process the form data

		// valid form
		if (result.hasErrors()) {
			session.setAttribute("message", Message.builder()
					.content("Please correct the following errors")
					.type(MessageType.red)
					.build());
			return "user/add_contact";
		}

		String username = Helper.getEmailOfLoggedInUser(authentication);

		User user = userService.getUserByEmail(username);
		
		
		
		Contact contact = new Contact();

		contact.setName(contactForm.getName());
		contact.setFavorite(contactForm.isFavorite());
		contact.setEmail(contactForm.getEmail());
		contact.setPhoneNumber(contactForm.getPhoneNumber());
		contact.setDescription(contactForm.getDescription());
		contact.setAddress(contactForm.getAddress());
		contact.setLinkedInLink(contactForm.getLinkedInLink());
		contact.setWebsiteLink(contactForm.getWebsiteLink());
		contact.setUser(user);
		
		if(contactForm.getContactImage()!=null && !contactForm.getContactImage().isEmpty()) {
			String filename = UUID.randomUUID().toString();
			//image process
			//logger.info("file information : {}",contactForm.getContactImage().getOriginalFilename());
			String fileURL =  imageService.uploadImage(contactForm.getContactImage(),filename);
			
			
			contact.setPicture(fileURL);
			contact.setCloudinaryImagePublicId(filename);
		}

		contactService.saveContact(contact);
		System.out.println(contactForm);
		
		session.setAttribute("message", Message.builder()
				.content("Your have successfully added a new contact")
				.type(MessageType.green)
				.build());
				
		return "redirect:/user/contacts/add";

	}
	
	//view contacts
	
	@RequestMapping
	public String viewContacts(
			@RequestParam(value = "page" , defaultValue = "0") int page
			,@RequestParam(value =  "size" , defaultValue = AppConstants.PAGE_SIZE + "") int size
			,@RequestParam(value =  "sortBy" , defaultValue = "name") String sortBy
			,@RequestParam(value =  "direction" , defaultValue = "asc") String direction
			,Model model, Authentication authentication) {
		//load all user contact
		String username =  Helper.getEmailOfLoggedInUser(authentication);
		User user = userService.getUserByEmail(username);
		Page<Contact> pageContacts= contactService.getByUser(user,page,size,sortBy,direction);
		
		model.addAttribute("pageContacts",pageContacts);
		model.addAttribute("pageSize",AppConstants.PAGE_SIZE);
		
		model.addAttribute("contactSearchForm", new ContactSearchForm());
		
		return "user/contacts";
	}
	
	
	@RequestMapping("/search")
    public String searchHandler(

            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication) {

        logger.info("field {} keyword {}", contactSearchForm.getField(), contactSearchForm.getValue());
       // logger.info("field {} keyword {}",  contactSearchForm.getValue());

        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContact = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy,
                    direction, user);
        }

        logger.info("pageContact {}", pageContact);

        model.addAttribute("contactSearchForm", contactSearchForm);

        model.addAttribute("pageContact", pageContact);

        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/search";
    }
	
	  // detete contact
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(
            @PathVariable("contactId") String contactId,
            HttpSession session) {
        contactService.deleteContact(contactId);
        logger.info("contactId {} deleted", contactId);

        session.setAttribute("message",
                Message.builder()
                        .content("Contact is Deleted successfully !! ")
                        .type(MessageType.green)
                        .build()

        );

        return "redirect:/user/contacts";
    }
    
    //update contact from view
    @GetMapping("view/{contactId}")
    public String updateContactFromView(
    		@PathVariable("contactId")String contactId,Model model) {
    	var contact = contactService.getById(contactId);
    	ContactForm contactForm = new ContactForm();
    	contactForm.setName(contact.getName());
    	contactForm.setEmail(contact.getEmail());
    	contactForm.setPhoneNumber(contact.getPhoneNumber());
    	contactForm.setDescription(contact.getDescription());
    	contactForm.setAddress(contact.getAddress());
    	contactForm.setFavorite(contact.isFavorite());
    	contactForm.setWebsiteLink(contact.getWebsiteLink());
    	contactForm.setLinkedInLink(contact.getLinkedInLink());
    	contactForm.setPicture(contact.getPicture());
    	model.addAttribute("contactForm", contactForm);
    	model.addAttribute("contactId", contactId);
    	return "user/update_conatct_view"; 
    	}
    
    @RequestMapping(value = "/update/{contactId}",method = RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,@Valid @ModelAttribute ContactForm contactForm,BindingResult bindingResult, Model model,HttpSession session) {
    	
    	if(bindingResult.hasErrors()) {
    		return "user/update_conatct_view";
    	}
    	
    	var con =  contactService.getById(contactId);
    	con.setId(contactId);
    	con.setName(contactForm.getName());
    	con.setEmail(contactForm.getEmail());
    	con.setPhoneNumber(contactForm.getPhoneNumber());
    	con.setAddress(contactForm.getAddress());
    	con.setDescription(contactForm.getDescription());
    	con.setFavorite(contactForm.isFavorite());
    	con.setLinkedInLink(contactForm.getLinkedInLink());
    	con.setWebsiteLink(contactForm.getWebsiteLink());
    	
    	
    	if(contactForm.getContactImage()!=null && !contactForm.getContactImage().isEmpty()) {
    		String fileName = UUID.randomUUID().toString();
        	String imageUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
        	con.setCloudinaryImagePublicId(fileName);
        	con.setPicture(imageUrl);
        	contactForm.setPicture(imageUrl);
    	}else {
    		
    	}
    	
    	var updateCon = contactService.updateContact(con);
    	logger.info("update contact {}",updateCon); 
    	
    	//model.addAttribute("message",Message.builder().content("Contact Updated").type(MessageType.green).build());
    	session.setAttribute("message", Message.builder()
				.content("Contact Updated.")
				.type(MessageType.green)
				.build());
    	return "redirect:/user/contacts/view/"+contactId;
		
	}
}
