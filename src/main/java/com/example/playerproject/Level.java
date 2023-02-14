package com.example.playerproject;

import lombok.Data;

@Data
public class Level {
    private int id;
    private String playerId;
    private String game;
    private PlayerLevel playerLevel;


    public Level(int id, String playerId, String game, PlayerLevel playerLevel) {
        this.id = id;
        this.playerId = playerId;
        this.game = game;
        this.playerLevel = playerLevel;
    }
}
