package com.example.RickAndMorty.services;

import com.example.RickAndMorty.responseBody.RickAndMortyResponseBody;
import com.example.RickAndMorty.schemas.RickAndMortyCharacters;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class APIServices {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CrudServices crudService;

    private String BASE_URL = "https://rickandmortyapi.com/api/character";
    String url = BASE_URL;

    public List<RickAndMortyResponseBody> getBodyFromAPI(){ // made this a function to reduce redundancy
        ResponseEntity<Map> response = restTemplate.exchange(url,HttpMethod.GET,null, Map.class); //sends get request to the external api
        Map<String,Object> responseBody = response.getBody(); // stores the JSON response in a Map<String,Object> format

        ObjectMapper mapper = new ObjectMapper();

        List<Map<String,Object>> resultsFromBody = (List<Map<String, Object>>) responseBody.get("results");

        return resultsFromBody.stream()
                .map(obj -> mapper.convertValue(obj, RickAndMortyResponseBody.class))
                .toList(); //maps each value as a RickAndMortyResponseBody object using mapper
    }


    public void saveCharaters(){

        List<RickAndMortyResponseBody> valuesFromBody = getBodyFromAPI();
        for(RickAndMortyResponseBody character : valuesFromBody){
            RickAndMortyCharacters entity = new RickAndMortyCharacters(character);
            crudService.saveCharacter(entity);
        }
    }

    public List<RickAndMortyCharacters> fetchAllCharacters(){
        return crudService.getAllFromDatabase();
    }

    public Optional<RickAndMortyCharacters> fetchCharacterById(Integer id){
        return crudService.findById(id);
    }

}
