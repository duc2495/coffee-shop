package coffeeshop.repository;

import java.util.Date;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import coffeeshop.model.User;

@Repository
public interface UserRepository {

	void insertUser(User user);

	User findByUsername(String username);

	User findByEmail(String email);

	User findByToken(String token);

	void resetToken(@Param("email") String email, @Param("token") String token, @Param("date") Date date);
}
