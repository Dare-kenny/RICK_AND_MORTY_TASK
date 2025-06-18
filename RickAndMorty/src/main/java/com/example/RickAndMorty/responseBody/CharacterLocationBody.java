package com.example.RickAndMorty.responseBody;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
@AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "Location_name")),
        @AttributeOverride(name = "url", column = @Column(name = "Location_url"))

})
public class CharacterLocationBody {

    @Column(name = "location_name")
    private String name;
    @Column(name = "location_url")
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
