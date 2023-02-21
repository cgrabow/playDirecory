package com.example.playerproject;


import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class Player {
    private int playerId;
    private String name;
    private String countryCode;

}
