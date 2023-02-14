package com.example.playerproject;


import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class Player {
    private String playerId;
    private String name;
    private String countryCode;


    public Player() {

    }

    public Player(String playerId, String name, String countryCode) {
        this.playerId = playerId;
        this.name = name;
        this.countryCode = countryCode;
    }

}
