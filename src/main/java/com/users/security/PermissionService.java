package com.users.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;
import static com.users.security.Role.*;

import com.users.repositories.UserRepository;

//The @Service annotation works a lot like component, but is targeted specifically to the service layer
@Service

public class PermissionService
{
	@Autowired
	UserRepository userRepo;

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

	//check if the current user has permission to edit information. allows an admin to edit any user, or an user to edit their own information
	public boolean canEditUser(long userId)
	{
		long currentUserId = userRepo.findByEmail(getToken().getName()).get(0).getId();
		return hasRole(ADMIN) || (hasRole(USER) && currentUserId == userId);
	}

}
