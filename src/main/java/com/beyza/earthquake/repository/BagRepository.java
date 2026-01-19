package com.beyza.earthquake.repository;

import com.beyza.earthquake.model.SavedRecord;

public interface BagRepository {
    void save(SavedRecord record);
}