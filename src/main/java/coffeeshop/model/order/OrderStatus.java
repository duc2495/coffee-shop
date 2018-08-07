package coffeeshop.model.order;

public enum OrderStatus {
	ORDERED(1), SHIPPING(2), FINISHED(3), CANCELED(0);
	private int value;

	OrderStatus(int value) {
		this.setValue(value);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
