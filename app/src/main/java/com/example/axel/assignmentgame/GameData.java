package com.example.axel.assignmentgame;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameData implements Serializable
{
    private static Area[][] grid;
	private Player player;
	private static GameData instance = null;
	private static Item[] mapItems;

    // Winning items
    private static Item jade = null;
    private static Item map = null;
    private static Item scraper = null;

    public GameData()
    {
        player = new Player();

        mapItems = generateItems();

        generateMap();
        player.setRowLocation(6);
        player.setColLocation(4);
    }

    public int getRows() { return grid.length; }
    public int getCols() { return grid[0].length; }
    public Player getPlayer() { return player; }
    public Area getLocation() { return grid[player.getRowLocation()][player.getColLocation()];}
    public Area[][] getGrid() { return grid; }

    public void setPlayer(Player inPlayer) { player = inPlayer; }

    public String toString(int row, int col) { return "Town: " + grid[row][col].isTown(); }

    public static GameData getInstance() {
        if (instance == null)
        {
            instance = new GameData();
        }
        return instance;
    }

    public static void generateMap()
    {
        Random random = new Random();
        grid = new Area[11][8];

        for (int ii = 0; ii < grid.length; ii++)
        {
            for (int jj = 0; jj < grid[0].length; jj++)
            {
                List<Item> items = new LinkedList<>();
                for (int k = 0; k < random.nextInt(10); k++)
                {
                    items.add(mapItems[random.nextInt(mapItems.length)]);
                }
                grid[ii][jj] = new Area(random.nextBoolean(), "", false, false);
                grid[ii][jj].setItems(items);
            }
        }
    }

    public Item[] generateItems()
    {
        //Create the items for use in the game
        //Array of hardcoded items for the random generation of areas
        Item[] hardItems = new Item[13];

        // Winning items
        jade = new Equipment("Jade Monkey", "A beautiful monkey. One of the requirements to win.", 5, 5.0, "none");
        map = new Equipment("Roadmap", "A map of the area. One of the requirements to win.", 5, 2.5, "none");
        scraper = new Equipment("Ice Scraper", "Scrapes ice. One of the requirements to win.", 5, 7.5, "none");

        // Hardcoded items
        hardItems[0] = new Equipment();
        hardItems[1] = new Equipment("Sword", "A sharp sword. Does nothing.", 15, 14.2, "None");
        hardItems[2] = new Equipment("Shield","A round shield. Does nothing.", 5, 12.8, "None");
        hardItems[3] = new Food();
        hardItems[4] = new Food("Eggplant", "Ew", 10, -25.5);
        hardItems[5] = new Food("Random food idk", "Seriously idk", 25, 1.5);
        hardItems[6] = new Food("Apple", "Healthy", 2, 8.5);
        hardItems[7] = jade;
        hardItems[8] = map;
        hardItems[9] = scraper;
        hardItems[10] = new Equipment("Portable Smell-O-Scope", "See items in surround areas.", 5, 10.0, "scope");
        hardItems[11] = new Equipment("Improbability Drive", "Regenerates all areas and items in areas. Does not affect player.", 5, -(Math.PI), "improbability");
        hardItems[12] = new Equipment("Ben Kenobi", "Immediately acquire all items in the area for free", 5, 0.0, "kenobi");

        return hardItems;
    }

    public static GameData destroySingleton() {
        instance = new GameData();
        return instance;
    }

    public static boolean winCondition()
    {
        return (instance.player.getEquipment().contains(jade) &&
                instance.player.getEquipment().contains(map) &&
                instance.player.getEquipment().contains(scraper));
    }
}
