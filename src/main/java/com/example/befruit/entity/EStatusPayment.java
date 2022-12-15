package com.example.befruit.entity;

public enum EStatusPayment {
	PAID("PAID"),
	UNPAID("UNPAID");

	private final String name;

	EStatusPayment(String s) {
		name = s;
	}

	public String getName() {
		return name;
	}
}
