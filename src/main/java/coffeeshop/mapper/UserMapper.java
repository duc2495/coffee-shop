package coffeeshop.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import coffeeshop.model.User;

public interface UserMapper {
	@Select("SELECT * FROM user WHERE username = #{username}")
	@Results(value = { @Result(property = "username", column = "username"),
			@Result(property = "fullName", column = "full_name"), @Result(property = "address", column = "address"),
			@Result(property = "phoneNumber", column = "phone_number"),
			@Result(property = "email", column = "email") })
	User findByUsername(String username);

	@Select("SELECT * FROM user WHERE email = #{email}")
	@Results(value = { @Result(property = "username", column = "username"),
			@Result(property = "fullName", column = "full_name"), @Result(property = "address", column = "address"),
			@Result(property = "phoneNumber", column = "phone_number"),
			@Result(property = "email", column = "email") })
	User findByEmail(String email);
}
