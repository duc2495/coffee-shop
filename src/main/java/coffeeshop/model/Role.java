package coffeeshop.model;

import lombok.Data;

@Data
public class Role {
	private String roleName;

	public Role() {

	}

	public Role(String roleName) {
		this.roleName = roleName;
	}
}
