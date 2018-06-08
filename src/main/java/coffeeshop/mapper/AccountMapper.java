package coffeeshop.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import coffeeshop.model.Account;
import coffeeshop.model.Role;

public interface AccountMapper {
	@Insert("INSERT INTO account(username,password,enabled,role_name) VALUES(#{username}, #{password}, #{enabled}, #{role_name})")
	void insert(Account account);

	@Select("SELECT * FROM account WHERE username = #{username}")
	@Results(value = { @Result(property = "username", column = "username"),
			@Result(property = "password", column = "password"), @Result(property = "enabled", column = "enabled"),
			@Result(property = "roleName", column = "role_name", one=@One(select = "getRoleName")) })
	Account findByUsername(String username);
	
	@Select("SELECT role_name FROM role WHERE role_name = #{roleName}")
    Role getRoleName(String roleName);
}
