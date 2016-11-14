package com.users.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.users.beans.Contact;
import com.users.beans.User;

public interface ContactRepository extends CrudRepository<Contact, Long> {

	Contact findByUserIdAndId(long userId, long id);
	
	List<Contact> findAllByUserIdOrderByFirstNameAscLastNameAsc(long userId);

	List<User> findByLastNameOrFirstNameOrEmailOrTwitterHandleOrFacebookUrlIgnoreCase(
			String lastName, String firstName, String email, String twitterHandle,
			String facebookUrl);

}
//Allows you to search for an user by any criteria

/*Takes in objects from class contact, and the data type long. 
Allows you to search contacts by userid and lastname, userid and email, or find all
*/