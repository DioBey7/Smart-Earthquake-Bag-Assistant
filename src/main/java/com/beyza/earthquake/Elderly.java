package com.beyza.earthquake;

public class Elderly extends Person {
	public Elderly(String name) {
		super(name);
	}

	@Override
	public String getCategoryFileName() {
		return "elderly_items.txt";
	}
	
	@Override
	public String toString() {
		return getName() + " (Elderly)";
	}
}
