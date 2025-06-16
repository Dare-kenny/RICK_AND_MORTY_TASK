package com.example.RickAndMorty.schemas;

import com.example.RickAndMorty.Enum.ApprovalStatus;
import com.example.RickAndMorty.responseBody.CharacterLocationBody;
import com.example.RickAndMorty.responseBody.OriginBody;
import com.example.RickAndMorty.responseBody.RickAndMortyResponseBody;
import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "CharacterData")
public class RickAndMortyCharacters {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String status;

    @Column
    private String species;

    @Column
    private String type;

    @Column
    private String gender;

    @Embedded
    private OriginBody origin;

    @Enumerated(EnumType.STRING)
    @Column
    private ApprovalStatus approvalStatus;

    @Embedded
    private CharacterLocationBody characterLocation;

    @Column(name = "imageurl")
    private String imageurl;

    @Column(name = "episode_url")
    @CollectionTable(name = "character_episodes",joinColumns = @JoinColumn(name = "id"))
    @ElementCollection
    private List<String> episode;

    @Column(name = "char_url")
    private String charUrl;

    @Column(name = "spawn_point")
    private String spawnPoint;

    public RickAndMortyCharacters() {
    }

    public RickAndMortyCharacters(RickAndMortyResponseBody responsedata) {
        this.name = responsedata.getName();
        this.status = responsedata.getStatus();
        this.species = responsedata.getSpecies();
        this.type = responsedata.getType();
        this.gender = responsedata.getGender();
        this.origin = responsedata.getOrigin();
        this.approvalStatus = responsedata.getApprovalStatus();
        this.characterLocation = responsedata.getLocation();
        this.imageurl = responsedata.getImage();
        this.episode = responsedata.getEpisode();
        this.charUrl = responsedata.getUrl();
        this.spawnPoint = responsedata.getCreated();
    }

    public RickAndMortyCharacters( String name, String status, String species, String type, String gender, OriginBody origin, ApprovalStatus approvalStatus, CharacterLocationBody characterLocation, String imageurl, List<String> episode, String charUrl, String spawnPoint) {

        this.name = name;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.origin = origin;
        this.approvalStatus = approvalStatus;
        this.characterLocation = characterLocation;
        this.imageurl = imageurl;
        this.episode = episode;
        this.charUrl = charUrl;
        this.spawnPoint = spawnPoint;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public OriginBody getOrigin() {
        return origin;
    }

    public void setOrigin(OriginBody origin) {
        this.origin = origin;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public CharacterLocationBody getCharacterLocation() {
        return characterLocation;
    }

    public void setCharacterLocation(CharacterLocationBody characterLocation) {
        this.characterLocation = characterLocation;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public List<String> getEpisode() {
        return episode;
    }

    public void setEpisode(List<String> episode) {
        this.episode = episode;
    }

    public String getCharUrl() {
        return charUrl;
    }

    public void setCharUrl(String charUrl) {
        this.charUrl = charUrl;
    }

    public String getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(String spawnPoint) {
        this.spawnPoint = spawnPoint;
    }
}
