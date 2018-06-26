package coffeeshop.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import coffeeshop.model.Role;

public interface RoleMapper {
	@Select("SELECT * FROM \"role\" WHERE role_name = #{roleName}")
	@Results(value = { @Result(property = "roleName", column = "role_name") })
	Role getRole(String roleName);
}
