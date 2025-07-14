package com.smc.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.smc.Repository.ContactRepo;
import com.smc.entities.Contact;
import com.smc.entities.User;
import com.smc.helper.ResourceNotFoundException;
import com.smc.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactRepo contactRepo;

	@Override
	public Contact saveContact(Contact contact) {
		String contactId = UUID.randomUUID().toString();
		contact.setId(contactId);
		return contactRepo.save(contact);
	}

	@Override
	public Contact updateContact(Contact contact) {
		// TODO Auto-generated method stub
		Contact contactOld = contactRepo.findById(contact.getId()).orElseThrow(()-> new ResourceNotFoundException("Contact Not Found"));
		contactOld.setName(contact.getName());
		contactOld.setEmail(contact.getEmail());
		contactOld.setPhoneNumber(contact.getPhoneNumber());
		contactOld.setAddress(contact.getAddress());
		contactOld.setDescription(contact.getDescription());
		contactOld.setPicture(contact.getPicture());
		contactOld.setFavorite(contact.isFavorite());
		contactOld.setWebsiteLink(contact.getWebsiteLink());
		contactOld.setLinkedInLink(contact.getLinkedInLink());
		contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());


		return contactRepo.save(contactOld);
	}

	@Override
	public List<Contact> getAll() {
		// TODO Auto-generated method stub
		return contactRepo.findAll();
	}

	@Override
	public void deleteContact(String id) {
		// TODO Auto-generated method stub
		var contact = contactRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Contact not found with this id " + id));
		contactRepo.delete(contact);
	}


	@Override
	public List<Contact> getByUserId(String userId) {
		// TODO Auto-generated method stub
		return contactRepo.findByUserId(userId);

	}

	@Override
	public Contact getById(String id) {
		// TODO Auto-generated method stub
		return contactRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Contact not found with this id " + id));
	}

	@Override
	public Page<Contact> getByUser(User user,int page,int size, String sortBy, String direction) {
		 Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		var pageable = PageRequest.of(page, size, sort);


	    return contactRepo.findByUser(user,pageable);
	    }

	@Override
    public Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user) {

        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndNameContaining(user, nameKeyword, pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order,
            User user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndEmailContaining(user, emailKeyword, pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy,
            String order, User user) {

        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndPhoneNumberContaining(user, phoneNumberKeyword, pageable);
    }


}
