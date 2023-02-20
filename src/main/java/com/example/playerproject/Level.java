package com.example.playerproject;

import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class Level {
    private int id;
    private int playerId;
    private Game game;
    private PlayerLevel playerLevel;


    public Level(int id, int playerId, Game game, PlayerLevel playerLevel) {
        this.id = id;
        this.playerId = playerId;
        this.game = game;
        this.playerLevel = playerLevel;
    }
}
