package coffeeshop.model;

import java.util.Date;

import lombok.Data;

@Data
public class User {
	private String fullName;
	private String address;
	private String phoneNumber;
	private String email;
	private String token;
	private Date expiryDate;
	private Account account;
}
