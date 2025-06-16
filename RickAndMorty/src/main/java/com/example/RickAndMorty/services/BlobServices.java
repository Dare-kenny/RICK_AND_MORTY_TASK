package com.example.RickAndMorty.services;

import com.example.RickAndMorty.Enum.ApprovalStatus;
import com.example.RickAndMorty.responseBody.RickAndMortyResponseBody;
import com.example.RickAndMorty.schemas.RickAndMortyCharacters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class BlobServices {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CrudServices crudService;

    private String JSONBLOB_URL = "https://jsonblob.com/api/jsonBlob";
    String url = JSONBLOB_URL;

    public String submitCharacterByIdandRetrieveById(Integer id){
        Optional<RickAndMortyCharacters> requestedCharacter = crudService.findById(id);
        if(requestedCharacter.isEmpty()){
            return "Character not found";
        }
        RickAndMortyCharacters characterBody = requestedCharacter.get();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RickAndMortyCharacters> request = new HttpEntity<>(characterBody,header);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,request, String.class);
        String bloburl = response.getHeaders().getLocation().toString();
        String blobId = bloburl.substring(bloburl.lastIndexOf("/")+1);
        return "BlobId: "+blobId;
    }


    public ResponseEntity<?> retrieveCharacterByBlobId(String blobId){
        String mainurl = url + "/" +blobId;

        try{
            ResponseEntity<RickAndMortyCharacters> response = restTemplate.exchange(mainurl,HttpMethod.GET,null,RickAndMortyCharacters.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Character Not found");
        }
    }

    public ResponseEntity<String> approveCharacterById(String blobId){
        String mainurl = url + "/"+blobId;

        try{
            ResponseEntity<RickAndMortyCharacters> response = restTemplate.exchange(mainurl,HttpMethod.GET,null, RickAndMortyCharacters.class);
            RickAndMortyCharacters approvableCharacter = response.getBody();
            if(approvableCharacter.getId() > 20){
                approvableCharacter.setApprovalStatus(ApprovalStatus.APPROVED);
                crudService.saveCharacter(approvableCharacter);

                return ResponseEntity.ok(approvableCharacter.getName()+" has been approved");
            }else{
                return ResponseEntity.ok("Character is already approved");
            }
        }catch (RestClientException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Character not found");
        }
    }
}
