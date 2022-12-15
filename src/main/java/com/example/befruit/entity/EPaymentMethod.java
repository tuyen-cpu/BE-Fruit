package com.example.befruit.entity;

public enum EPaymentMethod {
	COD("COD"),
	PAYPAL("PAYPAL");
	private final String name;

	EPaymentMethod(String s) {
		name = s;
	}

	public String getName() {
		return name;
	}
}
