package com.beyza.earthquake;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
	private static Scanner input = new Scanner(System.in);
	private static ItemLoader itemLoader = new ItemLoader();
	private static List<EmergencyBag> allBags = new ArrayList<>();
	
	public static void main(String[] args) {
		while(true) {
			System.out.println("\n== SMART EARTHQUAKE EMERGENCY BAG ASSISTANT ==");
			System.out.println("1- Create New Earthquake Bag");
			System.out.println("2- Add New Item to Category");
			System.out.println("3- List all bags");
			System.out.println("0- Exit");
			System.out.print("Your choice: ");
			
			String choice = input.nextLine(); 
			
			switch (choice) {
			  case "1":
				  createNewBag();
				  break;
			  case "2":
				  addNewItemtoCategory();
				  break;
			  case "3":
				  listAllBags();
				  break;
			  case "0":
				  System.out.println("Exiting the system...");
				  System.exit(0);
				  break;
			  default:
				  System.out.println("Invalid input.");
				
			}		
		}
	}
	
	private static void createNewBag() {
	   System.out.print("How many family members are there? :  ");
       int count = Integer.parseInt(input.nextLine());
       List<Person> familyMembers = new ArrayList<>();
      
       if (count != 0) {
        for (int i = 1 ; i <= count ; i++) {
        	System.out.print("\n" + i + ". " + "Person's name: ");
        	String name = input.nextLine();
        	
        	System.out.println("\nSelect an Category");
        	System.out.println("1- Adult");
        	System.out.println("2- Elderly");
        	System.out.println("3- Baby");
        	System.out.println("4- Chronic Patient");
        	
        	System.out.print("Selected Category: ");
        	String sec_choice = input.nextLine();
        	
        	switch (sec_choice) {
        	  case "1":
        		  familyMembers.add(new Adult(name));
        		  break;
        	  case "2":
        		  familyMembers.add(new Elderly(name));
        		  break;
        	  case "3":
        		  familyMembers.add(new Baby(name));
        		  break;
        	  case "4":
        		  familyMembers.add(new ChronicPatient(name));
        		  break;
        	  default:
        		  System.out.println("Invalid input. Defaulting to adult.");
        		  familyMembers.add(new Adult(name));
        		  break;
        	}
        }
        System.out.println("\nSelect Risk Level");
        System.out.println("1- HIGH");
        System.out.println("2- MEDIUM");
        System.out.println("3- LOW");
        
        System.out.print("Selected Risk Level: ");
        String risk_choice = input.nextLine();
        
        RiskLevel riskLevel;
        String baseFile;
        
        switch (risk_choice) {
          case "1":
        	  riskLevel = RiskLevel.HIGH;
        	  baseFile = "base_items_high.txt";
        	  break;
          case "2":
        	  riskLevel = RiskLevel.MEDIUM;
        	  baseFile = "base_items_medium.txt";
        	  break;
          case "3":
        	  riskLevel = RiskLevel.LOW;
        	  baseFile = "base_items_low.txt";
        	  break;
          default:
        	  riskLevel = RiskLevel.MEDIUM;
        	  baseFile = "base_items_medium.txt";
        	  
        }
        
        System.out.print("\nBag capacity (kg): ");
        double capacity = Double.parseDouble(input.nextLine());
        
        EmergencyBag bag = new EmergencyBag(capacity , familyMembers);
        
        for (Person p : familyMembers) {
        	List<Item> baseItems = itemLoader.loadItems(baseFile);
        	for (Item item : baseItems) {
        		try{
        			bag.addItem(item);
        			
        		}catch (OverweightBagException e){
        			System.out.println("Warning: " + e.getMessage());
        			
        		}
        	}
            List<Item> personalItems = itemLoader.loadItems(p.getCategoryFileName());
            for (Item item : personalItems) {
            	try {
            	    bag.addItem(item);
            	}catch (OverweightBagException e) {
            		System.out.println("Warning: " + e.getMessage());
            	}
            	
            }
        }
        allBags.add(bag);
        displaySummary(familyMembers , bag);
       }
       else {
    	   System.out.println("Invalid value.");
       }
	}
	
	private static void displaySummary(List<Person> members , EmergencyBag bag) {
		System.out.println("\n==== BAG SUMMARY ====\n");
		System.out.println("Family Members: ");
		
		for (Person p : members) {
			System.out.println(p.toString());
		}
		
		System.out.println("Items:");
		int index = 1;
		for (Item item : bag.getItems()) {
			System.out.printf("%d. %s\n   %.2f kg\n", index++, item.getName(), item.getWeight());		
		}
		System.out.printf("\nTotal weight: %.2f kg\n", bag.getCurrentWeight());
		System.out.printf("Maximum capacity: %.2f kg\n", bag.getMaxCapacity());
		
		if (bag.getCurrentWeight() <= bag.getMaxCapacity()) {
			System.out.println("Status: Within limit");
		}
		else {
			System.out.println("Status: Over limit");
		}
		System.out.println("\nTo export the summary please press 'E' on the keyboard");
		System.out.print("To directly go back to the main menu please press 'Enter' on the keyboard\n");
		String export_choice = input.nextLine();
		if (export_choice.equalsIgnoreCase("E")) {
			exportBagtoTextFile(bag);
		}

	}
	
	private static void exportBagtoTextFile(EmergencyBag bag) {
		String fileName = "bag_summary_" + System.currentTimeMillis() + ".txt";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("\n==== SMART EARTHQUAKE BAG SUMMARY ====\n");
            writer.write("Family Members:\n");
            for (Person p : bag.getFamilyMembers()) {
                writer.write("- " + p.toString() + "\n");
            }
            
            writer.write("\nItems:\n");
            int index = 1;
            for (Item item : bag.getItems()) {
                writer.write(index++ + ". " + item.getName() + " (" + item.getWeight() + " kg)\n");
            }
            
            writer.write("\n---------------------------\n");
            writer.write("Total Weight: " + String.format("%.2f", bag.getCurrentWeight()) + " kg\n");
            writer.write("Max Capacity: " + String.format("%.2f", bag.getMaxCapacity()) + " kg\n");
            
            if (bag.getCurrentWeight() <= bag.getMaxCapacity()) {
                writer.write("Status: Within limit\n");
            } else {
                writer.write("Status: Over limit\n");
            }
            
            System.out.println("Summary successfully exported to " + fileName);
            
        } catch (IOException e) {
            System.out.println("Error exporting file: " + e.getMessage());
        }
    }
	
	private static void addNewItemtoCategory() {
		System.out.println("\nSelect Category:");
		System.out.println("1- Adult");
		System.out.println("2- Elderly");
		System.out.println("3- Baby");
		System.out.println("4- Chronic Patient");
		System.out.print("Your choice: ");
		String new_item_choice = input.nextLine();
		
		String fileName = "";
		switch (new_item_choice) {
		  case "1":
			  fileName = "adult_items.txt";
			  break;
		  case "2":
			  fileName = "elderly_items.txt";
			  break;
		  case "3":
			  fileName = "baby_items.txt";
			  break;
		  case "4":
			  fileName = "chronic_items.txt";
			  break;
		  default:
			  System.out.println("Invalid category");
			  break;
			
		}
		System.out.print("Item name: ");
		String item_name = input.nextLine();
		
		System.out.print("Weight (kg) (e.g., 0.5): ");
		try {
			String weightStr = input.nextLine().replace(',', '.');
			double weight = Double.parseDouble(weightStr);
			itemLoader.appendItem(fileName , item_name , weight);
			System.out.printf("Item appended to the %s file.\n" , fileName);
			
		} catch (NumberFormatException e) {
			System.out.println("Invalid weight format.");
		}
		
	}
	
	private static void listAllBags() {
        if (allBags.isEmpty()) {
            System.out.println("No bags created yet.");
            return;
        }
        
        System.out.println("\n--- List of Created Bags ---\n");
        for (int i = 0; i < allBags.size(); i++) {
            EmergencyBag bag = allBags.get(i);
            System.out.println("Bag #" + (i + 1) + ": " + bag.getFamilyMembers().size() + " people");
            System.out.printf("Total Weight: %.2f kg%n" , bag.getCurrentWeight());
            System.out.print("----------------------------\n");
        }
	}
}
