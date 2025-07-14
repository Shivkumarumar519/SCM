package com.smc.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.smc.entities.Contact;
import com.smc.entities.User;

public interface ContactService {
	//save contact
	Contact saveContact(Contact contact);


	//update contact
	Contact updateContact(Contact contact);


	//get contact
	List<Contact> getAll();


	//get contact by id
	Contact getById(String id);

	//delete contact
	void deleteContact(String id);


	//search contact
	Page<Contact> searchByName(String nameKeyword,int size,int page,String sortBy,String order, User user);

	Page<Contact> searchByEmail(String emailKeyword,int size,int page,String sortBy,String order, User user);

	Page<Contact> searchByPhoneNumber(String phoneNumberKeyword,int size,int page,String sortBy,String order, User user);


	//get contacts by userid
	List<Contact> getByUserId(String userId);

	Page<Contact> getByUser(User user,int page, int size,  String sortField, String sortDirection);
}
