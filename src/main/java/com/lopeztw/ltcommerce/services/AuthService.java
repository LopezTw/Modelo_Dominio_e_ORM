package com.lopeztw.ltcommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lopeztw.ltcommerce.entities.User;
import com.lopeztw.ltcommerce.services.exceptions.ForbiddenException;

@Service
public class AuthService {
	
	@Autowired
	private UserService userService;
	
	public void validateSelfOrAdmin(long userId) {
		
		User me = userService.authenticated();
		
		// Se o ME(usuario) nao possuir o ROLE_ADMIN e nem for o mesmo recebido no parametro (for outra pessoa), lança exceçao forbidden.
		if (!me.hasRole("ROLE_ADMIN") && !me.getId().equals(userId)) {
			throw new ForbiddenException("Acess denied");
		}
	}
}
