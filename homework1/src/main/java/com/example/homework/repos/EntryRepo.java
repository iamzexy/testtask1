package com.example.homework.repos;

import com.example.homework.domain.Entry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EntryRepo extends CrudRepository<Entry, Integer> {
    List<Entry> findByContent(String content);
    List<Entry> findByTitle(String title);
    List<Entry> findById(int id);
}
