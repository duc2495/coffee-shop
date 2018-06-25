package coffeeshop.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import coffeeshop.model.Account;
import coffeeshop.model.Role;

public interface AccountMapper {
	@Insert("INSERT INTO account(username,password,enabled,role) VALUES(#{username}, #{password}, #{enabled}, #{role.roleName})")
	void insert(Account account);

	@Select("SELECT * FROM account WHERE username = #{username}")
	@Results(value = { @Result(property = "username", column = "username"),
			@Result(property = "password", column = "password"), @Result(property = "enabled", column = "enabled"),
			@Result(property = "role", javaType = Role.class, column = "role", one = @One(select = "getRole")) })
	Account findByUsername(String username);

	@Select("SELECT * FROM role WHERE role_name = #{roleName}")
	@Results(value = { @Result(property = "roleName", column = "role_name") })
	Role getRole(String roleName);
	
	@Update("UPDATE account SET enabled =#{enabled} WHERE username =#{username}")
	void updateEnabled(@Param("username") String username, @Param("enabled") String enabled);
	
	@Update("UPDATE account SET password =#{password} WHERE username =#{username}")
	void updatePassword(@Param("username") String username, @Param("password") String password);
}
