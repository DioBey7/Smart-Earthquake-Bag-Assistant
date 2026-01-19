package com.beyza.earthquake.model;

public class Item implements Weighable {
    private String name;
    private double weight;
    private double cost;
    private String category; 

    public Item(String name, double weight) {
        this.name = name;
        this.weight = weight;
        this.cost = 0;
        this.category = "Genel";
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}