package com.beyza.earthquake.service;

import com.beyza.earthquake.model.EmergencyBag;
import com.beyza.earthquake.model.Item;
import com.beyza.earthquake.exception.OverweightBagException;
import java.util.List;

public class BagService {
    public int calculateSurvivalScore(EmergencyBag bag) {
        int score = 0;
        List<Item> items = bag.getItems();
        for(Item i : items) {
            String n = i.getName().toLowerCase();
            if(n.contains("su") || n.contains("water")) score += 30;
            if(n.contains("gıda") || n.contains("food") || n.contains("konserve")) score += 20;
            if(n.contains("fener") || n.contains("light") || n.contains("torch")) score += 15;
            if(n.contains("düdük") || n.contains("whistle")) score += 10;
            if(n.contains("ilk yardım") || n.contains("aid")) score += 15;
            if(n.contains("radyo") || n.contains("radio")) score += 10;
        }
        return Math.min(score, 100);
    }

    public String getMissingItemsReport(EmergencyBag bag) {
        List<Item> items = bag.getItems();
        boolean w=false, f=false, l=false, aid=false, whis=false;
        
        for(Item i : items) {
            String n = i.getName().toLowerCase();
            if(n.contains("su") || n.contains("water")) w=true;
            if(n.contains("gıda") || n.contains("food") || n.contains("konserve")) f=true;
            if(n.contains("fener") || n.contains("light")) l=true;
            if(n.contains("yardım") || n.contains("aid")) aid=true;
            if(n.contains("düdük") || n.contains("whistle")) whis=true;
        }
        
        StringBuilder sb = new StringBuilder();
        if(!w) sb.append("- SU Eksik! / WATER Missing!\n");
        if(!f) sb.append("- GIDA Eksik! / FOOD Missing!\n");
        if(!l) sb.append("- FENER Eksik! / LIGHT Missing!\n");
        if(!aid) sb.append("- İLK YARDIM Eksik! / FIRST AID Missing!\n");
        if(!whis) sb.append("- DÜDÜK Eksik! / WHISTLE Missing!\n");
        
        if(sb.length() == 0) return "Her şey yolunda. / All good.";
        return sb.toString();
    }

    public void loadAutoItems(EmergencyBag bag, int riskIdx, int categoryIdx) {
        Object[][] baseItems = getRiskItems(riskIdx);
        addItemsFromArray(bag, baseItems);

        Object[][] categoryItems = getCategoryItems(categoryIdx);
        if (categoryItems != null) {
            addItemsFromArray(bag, categoryItems);
        }
    }


    private void addItemsFromArray(EmergencyBag bag, Object[][] items) {
        for(Object[] row : items) {
            try {
                String name = (String)row[0];
                double w = (double)row[1];
                double c = (double)row[2];
                String cat = (String)row[3];
                
                Item item = new Item(name, w);
                item.setCost(c);
                item.setCategory(cat);
                
                bag.addItem(item);
            } catch (OverweightBagException e) {
                System.out.println("Oto-yükleme sırasında kapasite doldu: " + e.getMessage());
            } catch (Exception e) {
            	
            }
        }
    }


    private Object[][] getRiskItems(int riskIdx) {
        Object[][] high = {
            {"Water 1.5L", 1.5, 10.0, "Risk: High"}, {"Water 1.5L", 1.5, 10.0, "Risk: High"},
            {"Tinned food", 0.5, 50.0, "Risk: High"}, {"First aid kit", 0.8, 250.0, "Risk: High"},
            {"Torch", 0.3, 100.0, "Risk: High"}, {"Whistle", 0.1, 20.0, "Risk: High"}
        };
        Object[][] medium = {
            {"Water 1.5L", 1.5, 10.0, "Risk: Medium"}, {"Tinned food", 0.5, 50.0, "Risk: Medium"},
            {"First aid kit", 0.8, 250.0, "Risk: Medium"}, {"Torch", 0.3, 100.0, "Risk: Medium"}
        };
        Object[][] low = {
            {"Water 1.5L", 1.5, 10.0, "Risk: Low"}, {"Tinned food", 0.5, 50.0, "Risk: Low"},
            {"Torch", 0.3, 100.0, "Risk: Low"}
        };
        return (riskIdx == 0) ? high : (riskIdx == 1) ? medium : low;
    }


    private Object[][] getCategoryItems(int catIdx) {

        if(catIdx == 2) return new Object[][] { 
            {"Baby nappy pack", 0.8, 150.0, "Category: Baby"}, {"Baby formula", 0.5, 200.0, "Category: Baby"},
            {"Wet wipes", 0.4, 50.0, "Category: Baby"}
        };
        if(catIdx == 3) return new Object[][] { 
            {"Medicine kit", 0.6, 100.0, "Category: Elderly"}, {"Walking stick", 0.7, 150.0, "Category: Elderly"}
        };
        if(catIdx == 4) return new Object[][] { 
            {"Chronic meds", 0.7, 50.0, "Category: Chronic"}, {"Spare battery", 0.3, 100.0, "Category: Chronic"}
        };
        return null;
    }
}
