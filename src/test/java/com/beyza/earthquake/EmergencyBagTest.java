package com.beyza.earthquake;

import org.junit.jupiter.api.Test;

import com.beyza.earthquake.exception.OverweightBagException;
import com.beyza.earthquake.model.EmergencyBag;
import com.beyza.earthquake.model.Item;

import static org.junit.jupiter.api.Assertions.*; 
import java.util.ArrayList;

public class EmergencyBagTest {
    @Test
    public void testAddItemWithinCapacity() {
        EmergencyBag bag = new EmergencyBag(10.0, new ArrayList<>());
        Item water = new Item("Su", 1.5);

        try {
            bag.addItem(water);
        } catch (OverweightBagException e) {
            fail("Kapasite yeterli olduğu halde hata fırlatıldı: " + e.getMessage());
        }

        assertEquals(1, bag.getItems().size(), "Çantadaki ürün sayısı 1 olmalı");
        assertEquals(1.5, bag.getCurrentWeight(), 0.001, "Çantanın ağırlığı 1.5 kg olmalı");
    }

    @Test
    public void testOverweightException() {
        EmergencyBag bag = new EmergencyBag(1.0, new ArrayList<>());
        Item heavyItem = new Item("Ağır Kaya", 5.0); 

        assertThrows(OverweightBagException.class, () -> {
            bag.addItem(heavyItem);
        }, "Kapasite aşıldığında OverweightBagException fırlatılmalıydı!");
    }
}