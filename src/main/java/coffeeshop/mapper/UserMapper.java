package coffeeshop.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import coffeeshop.model.User;

public interface UserMapper {
	@Insert("INSERT INTO \"user\"(username, fullname, address, phone_number, email, token)"
			+ " VALUES(#{account.username}, #{fullName}, #{address}, #{phoneNumber}, #{email}, #{token})")
	void create(User user);

	@Select("SELECT * FROM user WHERE username = #{username}")
	@Results(value = { @Result(property = "fullName", column = "fullname"),
			@Result(property = "address", column = "address"),
			@Result(property = "phoneNumber", column = "phone_number"), @Result(property = "email", column = "email"),
			@Result(property = "token", column = "token") })
	User findByUsername(String username);

	@Select("SELECT * FROM user WHERE email = #{email}")
	@Results(value = { @Result(property = "fullName", column = "fullname"),
			@Result(property = "address", column = "address"),
			@Result(property = "phoneNumber", column = "phone_number"), @Result(property = "email", column = "email"),
			@Result(property = "token", column = "token") })
	User findByEmail(String email);

	@Select("SELECT * FROM user WHERE 'token' = #{token}")
	@Results(value = { @Result(property = "fullName", column = "fullname"),
			@Result(property = "address", column = "address"),
			@Result(property = "phoneNumber", column = "phone_number"), @Result(property = "email", column = "email"),
			@Result(property = "token", column = "token") })
	User findByToken(String token);

	@Update("UPDATE user SET token =#{token} WHERE email =#{email}")
	void updateToken(@Param("email") String email, @Param("token") String token);
}
