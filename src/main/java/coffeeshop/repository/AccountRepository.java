package coffeeshop.repository;

import org.springframework.stereotype.Repository;
import coffeeshop.model.Account;

@Repository
public interface AccountRepository {

	void insertAccount(Account account);

	Account findAccountByUsername(String username);

	void updateAccount(Account account);
}
