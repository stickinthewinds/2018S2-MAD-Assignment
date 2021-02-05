package com.example.axel.assignmentgame;

public class GameDb
{
    private GameData gameData;
    private GameStore gameStore;

    public GameDb() {}

    public void load(GameStore inGameStore)
    {
        // ...
        gameData = GameData.getInstance();
        Area[][] grid = gameData.getGrid();

        gameStore = inGameStore;
        GameCursor cursor = gameStore.loadArea();

        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                cursor.getArea(grid);
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        cursor = gameStore.loadPlayer();
        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                gameData.setPlayer(cursor.getPlayer());
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        cursor = gameStore.loadItems();
        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                cursor.getItem(gameData);
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }
    }

    public void addArea(Area a, int rLoc, int cLoc)
    {
        Area[][] grid = gameData.getGrid();

        grid[rLoc][cLoc] = a;

        // ...
        gameStore.addArea(a, rLoc, cLoc);
    }

    public void editArea(Area a, int rLoc, int cLoc)
    {
        Area[][] grid = gameData.getGrid();

        grid[rLoc][cLoc] = a;
        // ...
        gameStore.updateArea(a, rLoc, cLoc);
    }

    public void removeArea(Area a, int rLoc, int cLoc)
    {
        Area[][] grid = gameData.getGrid();

        grid[rLoc][cLoc] = null;
        // ...
        gameStore.removeArea(a);
    }
}
