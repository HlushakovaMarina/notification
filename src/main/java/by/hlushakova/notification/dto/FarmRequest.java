package by.hlushakova.notification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FarmRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("location")
    private String location;

    public FarmRequest(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public FarmRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
