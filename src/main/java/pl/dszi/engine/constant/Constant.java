package pl.dszi.engine.constant;

import java.awt.*;

public class Constant {
    public static final int DEFAULT_CELL_SIZE = 64;
    public static final int DEFAULT_GAME_TILES_HORIZONTALLY = 13;
    public static final int DEFAULT_GAME_TILES_VERTICALLY = 11;
    public static final int DEFAULT_GAME_WIDTH = DEFAULT_CELL_SIZE * DEFAULT_GAME_TILES_HORIZONTALLY;
    public static final int DEFAULT_GAME_HEIGHT = DEFAULT_CELL_SIZE * DEFAULT_GAME_TILES_VERTICALLY;
    public static final int DEFAULT_PLAYER_HP = 3;
    public static int BASIC_BOMB_EXPLOSION_TIMER = 0;
    public static int BASIC_BOMB_EXPLOSION_BURNING_TIMER = 0;
    public static final int DEFAULT_NODE_COST = 1;
    public static final int IMMORTALITY_TIMER = 3;
    public static String PLAYER_1_NAME = "Player 1";
    public static String PLAYER_2_NAME = "Player 2";
    public static int DEFAULT_SPEED = 4;
    public static final Point PLAYER_1_STARTINGLOCATION = new Point(0, 0);
    public static final Point PLAYER_2_STARTINGLOCATION = new Point(DEFAULT_CELL_SIZE * (Constant.DEFAULT_GAME_TILES_HORIZONTALLY - 1), DEFAULT_CELL_SIZE * (Constant.DEFAULT_GAME_TILES_VERTICALLY - 1));
    public static final int MAXIMUM_CRATE_AMOUNT = 143;
    public static final int MAXIMUM_CRATE_AMOUNT_LOCATION = 93;
    public static final int GENERATIONS_TO_BEAT_TO_GENERATE_CRATES = 20;
    public static final int POPULATIONS_TO_POP_NEW_GENERATION = 10;
    public static final int DEFAULT_CRATE_AMOUNT = 70;
    public static final int MAX_BOOSTER_CRATES = 5;
    public static final double BOOSTER_CRATE_THRESHOLD = 80;
    public static final double GA_UNIFORM_RATE = 0.5;
    public static final int GA_TOURNAMENT_SIZE = 2;
    public static final boolean GA_ELITISM = false;
}