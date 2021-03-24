package com.ween.service;

import com.ween.entity.Participant;
import com.ween.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository repository;

    public void saveOrUpdate(Participant participant){
        repository.save(participant);
    }

    public void findById(String id){
        repository.findById(id);
    }
}
