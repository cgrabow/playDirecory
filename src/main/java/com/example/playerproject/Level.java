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
}
