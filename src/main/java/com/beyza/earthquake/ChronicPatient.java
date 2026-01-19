package com.beyza.earthquake;

public class ChronicPatient extends Person {
	public ChronicPatient(String name) {
		super(name);
	}
	
	@Override
	public String getCategoryFileName() {
		return "chronic_items.txt";
	}
	
	@Override
	public String toString() {
		return getName() + " (Chronic Patient)";
	}
}
