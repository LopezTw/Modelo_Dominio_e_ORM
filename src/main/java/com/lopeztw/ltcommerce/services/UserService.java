package com.lopeztw.ltcommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lopeztw.ltcommerce.dto.UserDTO;
import com.lopeztw.ltcommerce.entities.Role;
import com.lopeztw.ltcommerce.entities.User;
import com.lopeztw.ltcommerce.projections.UserDetailsProjection;
import com.lopeztw.ltcommerce.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepository rep;
	
	// Retornar na aula "Implementando o checklist do Spring Security PARTE 2" p/ revisar o codigo feito;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserDetailsProjection> result = rep.searchUserAndRolesByEmail(username);
		if(result.size() == 0) {
			throw new UsernameNotFoundException("Email Not Found");
		}
		User user = new User();
		user.setEmail(username);
		user.setPassword(result.get(0).getPassword());
		
		for (UserDetailsProjection projection : result) {
			user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
		}
		
		return user;
	}

	protected User authenticated() {  // Nao disponivel pra classes de outros pacotes
		try {
			
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
		String username = jwtPrincipal.getClaim("username");
		
		return rep.findByEmail(username).get();
		}
		catch(Exception e) {
			throw new UsernameNotFoundException("Email Not Found");
		}
	}
	
	@Transactional(readOnly = true)
	public UserDTO getMe() {
		User user = authenticated();
		return new UserDTO(user);
	}
	
}
