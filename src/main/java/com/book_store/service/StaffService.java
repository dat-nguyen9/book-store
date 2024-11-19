package com.book_store.service;

import com.book_store.entity.Staff;
import com.book_store.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    public Staff get(int id) {
        return staffRepository.findById(id).get();
    }
}
