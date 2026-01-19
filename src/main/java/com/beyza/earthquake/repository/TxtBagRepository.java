package com.beyza.earthquake.repository;

import com.beyza.earthquake.model.SavedRecord;
import com.beyza.earthquake.model.Item;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TxtBagRepository implements BagRepository {

    @Override
    public void save(SavedRecord record) {
        String fileName = record.getRecordName().replaceAll("\\s+", "_") + "_Plan.txt";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("=== AKILLI DEPREM ÇANTASI PLANI ===\n");
            writer.write("Kayıt Adı: " + record.getRecordName() + "\n");
            writer.write("Tip: " + record.getRecordType() + "\n");
            writer.write("Tarih: " + record.getDate() + "\n\n");
            
            double grandTotalWeight = 0;
            double grandTotalCost = 0;
            
            for(SavedRecord.SubBag sub : record.getBags()) {
                writer.write(">> " + sub.ownerName + " (" + sub.ownerType + ")\n");
                writer.write("--------------------------------\n");
                
                double subCost = 0;
                for(Item i : sub.items) {
                    writer.write(String.format(" - %-20s : %5.2f kg | %6.2f TL/$\n", 
                        i.getName(), i.getWeight(), i.getCost()));
                    subCost += i.getCost();
                }
                
                writer.write("--------------------------------\n");
                writer.write(String.format("   [Alt Toplam: %.2f kg | %.2f TL/$]\n\n", 
                    sub.totalWeight, subCost));
                
                grandTotalWeight += sub.totalWeight;
                grandTotalCost += subCost;
            }
            
            writer.write("================================\n");
            writer.write(String.format("GENEL TOPLAM AĞIRLIK: %.2f kg\n", grandTotalWeight));
            writer.write(String.format("TOPLAM TAHMİNİ MALİYET: %.2f TL/$", grandTotalCost));
            
            System.out.println("Dosya başarıyla kaydedildi: " + fileName);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
