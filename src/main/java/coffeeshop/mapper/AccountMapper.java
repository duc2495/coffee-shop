package coffeeshop.mapper;

import org.apache.ibatis.annotations.Param;
import coffeeshop.model.Account;

public interface AccountMapper extends RoleMapper {

	void insert(Account account);

	Account findAccountByUsername(String username);

	void updateEnabled(@Param("username") String username, @Param("enabled") boolean enabled);

	void updatePassword(@Param("username") String username, @Param("password") String password);
}
