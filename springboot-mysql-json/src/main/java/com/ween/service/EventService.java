package com.ween.service;

import com.ween.entity.Event;
import com.ween.repository.EventRepository;
import com.ween.specifation.CustomSpecification;
import com.ween.specifation.SpecificationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    public void saveOrUpdate(Event event){
        repository.save(event);
    }

    public void findById(String id){
        repository.findById(id);
    }

    public Page<Event> findAll(String id, String city, Pageable pageable){
        Map<String, Object> param=new HashMap<>();
        return repository.findAll(SpecificationHandler.analysisParam(Event.class,param),pageable);
    }
}
