package com.beyza.earthquake;

public class Item implements Weighable{
	private String name;
	private double weight;
	
	public Item (String name , double weight) {
		
		this.name = name;
		this.weight = weight;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public double getWeight() {
		return weight;
	}
	
	@Override 
	public String toString(){
		return name;	
	}

	public void setCost(double c) {
		// TODO Auto-generated method stub
		
	}

	public double getCost() {
		// TODO Auto-generated method stub
		return 0;
	}


}
