package com.example.RickAndMorty.services;

import com.example.RickAndMorty.enum_values.ApprovalStatus;
import com.example.RickAndMorty.repositories.CharacterRepository;
import com.example.RickAndMorty.schemas.CharacterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CrudServices { // Service for saving,deleting and creating Characters

    @Autowired
    private CharacterRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public void saveCharacter(CharacterEntity entities){
        repository.save(entities);
    }

    public String createCharacter( @RequestBody CharacterEntity entity){
        entity.setApprovalStatus(ApprovalStatus.NOT_APPROVED); // The new/created Characters have their approval status to be saved as "NOT_APPROVED" at default
        repository.save(entity);
        return entity.getName()+" has been created and added to database";
    }

    public String deleteCharacter(Integer id){

        Optional<CharacterEntity> deletedEntity = repository.findById(id); // I used Optional for null-value containment

        if (deletedEntity.isPresent()){
            repository.deleteById(id);
            return deletedEntity.get().getName()+" has been removed from database";
        }else{
            return "Character with ID "+id+" not found";
        }
    }

    public Optional<CharacterEntity> findById(Integer id){
        return repository.findById(id);
    }

    public List<CharacterEntity> getAllFromDatabase(){
        return repository.findAll();
    }



}
