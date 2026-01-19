package com.beyza.earthquake;
import java.util.ArrayList;
import java.util.List;

public class EmergencyBag {
	private List<Item> items;
	private double maxCapacity;
	private double currentWeight;
	private List<Person> familyMembers;
	
	public EmergencyBag (double maxCapacity , List<Person> familyMembers){
		this.items = new ArrayList<>();
		this.maxCapacity = maxCapacity;
		this.familyMembers = familyMembers;
		this.currentWeight = 0.0;
	}
	
	public void addItem(Item item) throws OverweightBagException{
		if (currentWeight + item.getWeight() > maxCapacity) {
			throw new OverweightBagException("Item '" + item.getName() + "' (" + item.getWeight() + " kg) exceeds bag capacity!");
		}
		items.add(item);
		currentWeight += item.getWeight();	
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public List<Person> getFamilyMembers(){
		return familyMembers;
	}
	
	public double getMaxCapacity() {
		return maxCapacity;	
	}
	
	public double getCurrentWeight() {
		 return currentWeight;
	 }
}
