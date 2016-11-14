package com.users.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.users.beans.ContactImage;

public interface ContactImageRepository extends CrudRepository<ContactImage, Long>
{

	List<ContactImage> findByContactId(long contactId);
}
/*
Finds objects of the contactimage class by an id value (data type long)
*/