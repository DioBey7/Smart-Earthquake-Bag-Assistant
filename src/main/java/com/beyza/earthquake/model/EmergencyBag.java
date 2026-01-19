package com.beyza.earthquake.model;

import com.beyza.earthquake.exception.OverweightBagException;

import java.util.ArrayList;
import java.util.List;

public class EmergencyBag {
    private double capacity;
    private List<Item> items;
    private List<Person> familyMembers; 

    public EmergencyBag(double capacity) {
        this.capacity = capacity;
        this.items = new ArrayList<>();
        this.familyMembers = new ArrayList<>();
    }

    public EmergencyBag(double capacity, List<Person> familyMembers) {
        this.capacity = capacity;
        this.items = new ArrayList<>();
        this.familyMembers = familyMembers != null ? familyMembers : new ArrayList<>();
    }

    public void addItem(Item item) throws OverweightBagException {
        if (getCurrentWeight() + item.getWeight() > capacity) {
            throw new OverweightBagException("Bag capacity exceeded! (Max: " + capacity + "kg)");
        }
        items.add(item);
    }

    public void removeItem(String name, double weight) {
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.getName().equals(name) && item.getWeight() == weight) {
                items.remove(i);
                break; 
            }
        }
    }

    public double getCurrentWeight() {
        double sum = 0;
        for (Item i : items) {
            sum += i.getWeight();
        }
        return sum;
    }
    
    public double getTotalCost() {
        double sum = 0;
        for (Item i : items) {
            sum += i.getCost();
        }
        return sum;
    }

    public List<Item> getItems() { 
        return items; 
    }

    public double getMaxCapacity() { 
        return capacity; 
    }

    public double getCapacity() { 
        return capacity; 
    }

    public List<Person> getFamilyMembers() {
        return familyMembers;
    }

    public void addFamilyMember(Person p) {
        this.familyMembers.add(p);
    }
}