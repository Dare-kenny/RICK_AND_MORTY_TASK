package com.example.RickAndMorty.services;

import com.example.RickAndMorty.enum_values.ApprovalStatus;
import com.example.RickAndMorty.schemas.CharacterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ApprovalServices {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CrudServices crudService;

    private String JSONBLOB_URL = "https://jsonblob.com/api/jsonBlob";
    String url = JSONBLOB_URL;

    public String submitCharacterByIdandRetrieveById(Integer id){
        Optional<CharacterEntity> requestedCharacter = crudService.findById(id);
        if(requestedCharacter.isEmpty()){
            return "Character not found";
        }
        CharacterEntity characterBody = requestedCharacter.get();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON); // To indicate that the data i'm sending is a JSON type

        HttpEntity<CharacterEntity> request = new HttpEntity<>(characterBody,header); //wrapped the characterEntity and the header into a full request

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,request, String.class); //sending the post request to the url
        String bloburl = response.getHeaders().getLocation().toString();
        String blobId = bloburl.substring(bloburl.lastIndexOf("/")+1);
        return "BlobId: "+blobId;
    }


    public ResponseEntity<?> retrieveCharacterByBlobId(String blobId){
        String mainurl = url + "/" +blobId;

        try{
            ResponseEntity<CharacterEntity> response = restTemplate.exchange(mainurl,HttpMethod.GET,null, CharacterEntity.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Character Not found");
        }
    }

    public ResponseEntity<String> approveCharacterById(String blobId){
        String mainurl = url + "/"+blobId;

        try{
            ResponseEntity<CharacterEntity> response = restTemplate.exchange(mainurl,HttpMethod.GET,null, CharacterEntity.class);
            CharacterEntity approvableCharacter = response.getBody();
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
