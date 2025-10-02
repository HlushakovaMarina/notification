package by.hlushakova.notification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FruitRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("color")
    private String color;

    @JsonProperty("weight")
    private Double weight;

    public FruitRequest() {
    }

    public FruitRequest(String name, String color, Double weight) {
        this.name = name;
        this.color = color;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
