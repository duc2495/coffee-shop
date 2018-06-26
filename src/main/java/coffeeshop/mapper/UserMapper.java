package coffeeshop.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import coffeeshop.model.Account;
import coffeeshop.model.User;

public interface UserMapper extends AccountMapper {
	@Insert("INSERT INTO \"user\"(username, fullname, address, phone_number, email, token, expiry_date)"
			+ " VALUES(#{account.username}, #{fullName}, #{address}, #{phoneNumber}, #{email}, #{token}, #{expiryDate})")
	void create(User user);

	@Select("SELECT * FROM \"user\" WHERE username =#{username}")
	@Results(value = { @Result(property = "fullName", column = "fullname"),
			@Result(property = "address", column = "address"),
			@Result(property = "phoneNumber", column = "phone_number"), @Result(property = "email", column = "email"),
			@Result(property = "token", column = "token"), @Result(property = "expiryDate", column = "expiry_date"),
			@Result(property = "account", javaType = Account.class, column = "username", one = @One(select = "findAccountByUsername")) })
	User findByUsername(String username);

	@Select("SELECT * FROM \"user\" WHERE email =#{email}")
	@Results(value = { @Result(property = "fullName", column = "fullname"),
			@Result(property = "address", column = "address"),
			@Result(property = "phoneNumber", column = "phone_number"), @Result(property = "email", column = "email"),
			@Result(property = "token", column = "token"), @Result(property = "expiryDate", column = "expiry_date"),
			@Result(property = "account", javaType = Account.class, column = "username", one = @One(select = "findAccountByUsername")) })
	User findByEmail(String email);

	@Select("SELECT * FROM \"user\" WHERE token =#{token}")
	@Results(value = { @Result(property = "fullName", column = "fullname"),
			@Result(property = "address", column = "address"),
			@Result(property = "phoneNumber", column = "phone_number"), @Result(property = "email", column = "email"),
			@Result(property = "token", column = "token"), @Result(property = "expiryDate", column = "expiry_date"),
			@Result(property = "account", javaType = Account.class, column = "username", one = @One(select = "findAccountByUsername")) })
	User findByToken(String token);

	@Update("UPDATE \"user\" SET token =#{token}, expiry_date =#{date} WHERE email =#{email}")
	void resetToken(@Param("email") String email, @Param("token") String token, @Param("date") Date date);
}
