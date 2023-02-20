package com.example.playerproject;


import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class Player {
    private int playerId;
    private String name;
    private String countryCode;


    public Player() {

    }

    public Player(int playerId, String name, String countryCode) {
        this.playerId = playerId;
        this.name = name;
        this.countryCode = countryCode;
    }

}
