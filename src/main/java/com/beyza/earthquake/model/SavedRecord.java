package com.beyza.earthquake.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SavedRecord {
    private String recordName;
    private String recordType; 
    private String date;
    private List<SubBag> bags;

    public SavedRecord(String recordName, String recordType) {
        this.recordName = recordName;
        this.recordType = recordType;
        this.date = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date());
        this.bags = new ArrayList<>();
    }

    public void addMemberBag(String ownerName, String ownerType, EmergencyBag bag) {
        List<Item> itemsCopy = new ArrayList<>(bag.getItems());
        bags.add(new SubBag(ownerName, ownerType, itemsCopy, bag.getCurrentWeight()));
    }
    
    @Override
    public String toString() {
        return date + " | " + recordName + " (" + recordType + ")";
    }

    public String getRecordName() { return recordName; }
    public String getRecordType() { return recordType; }
    public String getDate() { return date; }
    public List<SubBag> getBags() { return bags; }

    public static class SubBag {
        public String ownerName;
        public String ownerType;
        public List<Item> items;
        public double totalWeight;

        public SubBag(String name, String type, List<Item> items, double weight) {
            this.ownerName = name;
            this.ownerType = type;
            this.items = items;
            this.totalWeight = weight;
        }
    }
}