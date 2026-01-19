package com.beyza.earthquake.model;

public class Adult extends Person {
	public Adult(String name) {
		super(name);
	}
	
	@Override
	public String getCategoryFileName() {
		return "adult_items.txt";
	}
   
	@Override
	public String toString() {
		return getName() + " (Adult)";
	}
}
