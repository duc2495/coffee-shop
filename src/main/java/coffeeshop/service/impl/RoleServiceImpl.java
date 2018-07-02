package coffeeshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffeeshop.model.Role;
import coffeeshop.repository.RoleRepository;
import coffeeshop.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Role getRole(String roleName) {
		return roleRepository.findRoleByName(roleName);
	}

}
