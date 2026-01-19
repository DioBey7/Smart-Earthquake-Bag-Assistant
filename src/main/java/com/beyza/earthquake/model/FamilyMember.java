package com.beyza.earthquake.model;

public class FamilyMember{
	private String name;
    private String type; 
    private double capacity;
    private EmergencyBag bag;

    public FamilyMember(String name, String type, double capacity) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.bag = new EmergencyBag(capacity);
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public double getCapacity() { return capacity; }
    public EmergencyBag getBag() { return bag; }
}
