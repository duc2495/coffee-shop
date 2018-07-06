package coffeeshop.repository;

import org.springframework.stereotype.Repository;
import coffeeshop.model.Role;

@Repository
public interface RoleRepository {
	Role findRoleByName(String roleName);
}
