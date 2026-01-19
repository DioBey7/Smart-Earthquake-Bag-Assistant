package com.beyza.earthquake;

public class Baby extends Person {
	public Baby(String name) {
		super(name);
	}

	@Override
	public String getCategoryFileName() {
		return "baby_items.txt";
	}
	
	@Override
	public String toString() {
		return getName() + " (Baby)";
	}
}
