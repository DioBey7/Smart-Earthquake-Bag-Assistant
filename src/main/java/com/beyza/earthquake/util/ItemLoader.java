package com.beyza.earthquake.util;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.beyza.earthquake.model.Item;

public class ItemLoader {
	public List<Item> loadItems(String fileName){
		List<Item> items = new ArrayList<>();
		File file = new File(fileName);
		
		if (!file.exists()) {
			return items;
		}
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))){
			String line;
			
			while  ((line = reader.readLine()) != null) {
				String[] parts = line.split(";");
				if (parts.length == 2) {
					String name = parts[0].trim();
                    double weight = Double.parseDouble(parts[1].trim());
                    items.add(new Item(name, weight));
					
				}
			}
		} catch (IOException | NumberFormatException e) {
            System.err.println("Error reading file: " + fileName);
		}
		return items;
	}

	
	public void appendItem(String fileName, String itemName, double weight) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.newLine(); 
            writer.write(itemName + "; " + weight);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + fileName);
        }
    }
}
