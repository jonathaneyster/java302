package com.users.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static com.users.security.Role.*;

import com.users.repositories.UserRepository;
import com.users.repositories.ContactRepository;

//The @Service annotation works a lot like component, but is targeted specifically to the service layer
@Service

public class PermissionService
{
	@Autowired
	UserRepository userRepo;

	@Autowired
	ContactRepository contactRepo;

	private UsernamePasswordAuthenticationToken getToken()
	{
		return (UsernamePasswordAuthenticationToken) getContext().getAuthentication();
	}

	public boolean hasRole(Role role)
	{
		for (GrantedAuthority ga : getToken().getAuthorities())
		{
			if (role.toString().equals(ga.getAuthority()))
			{
				return true;
			}
		}
		return false;
	}

	public boolean canEditUser(long userId)
	{
		return hasRole(ADMIN) || (hasRole(USER) && findCurrentUserId() == userId);
	}

	public long findCurrentUserId()
	{
		return userRepo.findByEmail(getToken().getName()).get(0).getId();
	}

	public boolean canEditContact(long contactId)
	{
		return hasRole(USER) && contactRepo.findByUserIdAndId(findCurrentUserId(), contactId) != null;
	}
	//this allows any user to edit their contacts
}