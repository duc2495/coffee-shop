package coffeeshop.mapper;

import coffeeshop.model.Role;

public interface RoleMapper {
	Role findRoleByName(String roleName);
}
