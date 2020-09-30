package com.example.homework;

import com.example.homework.domain.Entry;
import com.example.homework.repos.EntryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller

public class GreetingsController {
    @Autowired
    private EntryRepo entryRepo;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Entry> entries = entryRepo.findAll();
        model.put("entries", entries);
        return "main";
    }

    @GetMapping("entry")
    public String entry(Map<String, Object> model) {
        Iterable<Entry> entries = entryRepo.findAll();
        model.put("entries", entries);
        return "entry";
    }

    @GetMapping("newEntry")
    public String newEntry(Map<String, Object> model) {
        Iterable<Entry> entries = entryRepo.findAll();
        model.put("entries", entries);
        return "newEntry";
    }



    @PostMapping("add")
    public String add(@RequestParam String title, @RequestParam String content, Map<String, Object> model) {
        Entry entry = new Entry(title, content);
        if (entry.getContent()==null || entry.getContent().isEmpty()){
            entry.setContent("пустая заметка");
        }
        if (entry.getTitle()==null || entry.getTitle().isEmpty()){
            entry.setTitle(content);
        }

        entryRepo.save(entry);


        Iterable<Entry> entries = entryRepo.findAll();
        model.put("entries", entries);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Entry> entries = entryRepo.findAll();
        if (filter != null && !filter.isEmpty()) {
            Iterator<Entry> itr = entries.iterator();
            while (itr.hasNext()){
                Entry entry = itr.next();
                boolean first = !entry.getTitle().contains(filter);
                boolean second = !entry.getContent().contains(filter);
                    if (first && second) {
                        itr.remove();
                    }
            }
        }
        model.put("entries", entries);
        return "main";
    }



    @RequestMapping(value = "open", method = RequestMethod.GET)
    public String open(@RequestParam int id, Map<String, Object> model) {
        Iterable<Entry> entries;
//        if (title != null && !title.isEmpty()){
//            entries = entryRepo.findByTitle(title);
//        } else {
//            entries = entryRepo.findAll();
//        }
        entries = entryRepo.findById(id);
        model.put("entries", entries);
        return "entry";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String remove(@RequestParam int id, Map<String, Object> model) {
        entryRepo.delete(entryRepo.findById(id).get(0));
        Iterable<Entry> entries = entryRepo.findAll();
        model.put("entries", entries);
        return "main";
    }

    @RequestMapping(value = "update",  method = RequestMethod.POST)
    public String update(@RequestParam int id, @RequestParam String title, @RequestParam String content, Map<String, Object> model) {
        Entry entry = new Entry(id, title, content);
        entryRepo.save(entry);
        Iterable<Entry> entries = entryRepo.findAll();
        model.put("entries", entries);
        return "main";
    }
}
