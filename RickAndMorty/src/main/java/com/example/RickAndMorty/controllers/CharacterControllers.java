package com.example.RickAndMorty.controllers;

import com.example.RickAndMorty.schemas.RickAndMortyCharacters;
import com.example.RickAndMorty.services.APIServices;
import com.example.RickAndMorty.services.CrudServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class CharacterControllers {

    @Autowired
    private APIServices apiService;

    @Autowired
    private CrudServices crudServices;

    @GetMapping("/saveAllCharacters") // saves All Characters (should be done just once)
    public String saveToDatabase(){
        apiService.saveCharaters();
        return "All characters saved to database";
    }

    @GetMapping("/characters") // Shows All characters in the database
    public List<RickAndMortyCharacters> showAllCharacters(){
        return apiService.fetchAllCharacters();
    }

    @GetMapping("/fetch/{id}") //shows character according to it's id
    public Optional<RickAndMortyCharacters> fetchCharacterById(@PathVariable Integer id){
        return apiService.fetchCharacterById(id);
    }

    @GetMapping("/create") //Creates and adds a new character to my database
    public String createCharacter(@RequestBody RickAndMortyCharacters entity){
        return crudServices.createCharacter(entity);
    }

    @GetMapping("/delete/{id}") // deletes a character according to its id
    public String deleteCharacterById(@PathVariable Integer id){
        return crudServices.deleteCharacter(id);
    }
}
