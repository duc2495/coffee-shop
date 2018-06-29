package coffeeshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffeeshop.mapper.RoleMapper;
import coffeeshop.model.Role;
import coffeeshop.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public Role getRole(String roleName) {
		return roleMapper.findRoleByName(roleName);
	}

}
