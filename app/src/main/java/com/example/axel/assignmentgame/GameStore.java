package com.example.axel.assignmentgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.axel.assignmentgame.DatabaseSchema.*;

public class GameStore
{
    private SQLiteDatabase db;
    public GameStore(Context context)
    {
        this.db = new DbHelper(
                context.getApplicationContext()
        ).getWritableDatabase();
    }

    public void addArea(Area a, int rLoc, int cLoc)
    {
        ContentValues cv = new ContentValues();

        int type = 0, starred = 0, explored = 0;
        if (!a.isTown())
            type = 1;
        if (!a.isStarred())
            starred = 1;
        if (!a.isExplored())
            explored = 1;

        cv.put(AreaTable.Cols.ID, a.getId());
        cv.put(AreaTable.Cols.ROWLOCATION, rLoc);
        cv.put(AreaTable.Cols.COLLOCATION, cLoc);
        cv.put(AreaTable.Cols.TYPE, type);
        cv.put(AreaTable.Cols.STARRED, starred);
        cv.put(AreaTable.Cols.EXPLORED, explored);
        cv.put(AreaTable.Cols.DESCRIPTION, a.getDescription());
        db.insert(AreaTable.NAME, null, cv);
    }

    public void updateArea(Area a, int rLoc, int cLoc)
    {
        ContentValues cv = new ContentValues();

        int type = 0, starred = 0, explored = 0;
        if (!a.isTown())
            type = 1;
        if (!a.isStarred())
            starred = 1;
        if (!a.isExplored())
            explored = 1;

        cv.put(AreaTable.Cols.ID, a.getId());
        cv.put(AreaTable.Cols.ROWLOCATION, rLoc);
        cv.put(AreaTable.Cols.COLLOCATION, cLoc);
        cv.put(AreaTable.Cols.TYPE, type);
        cv.put(AreaTable.Cols.STARRED, starred);
        cv.put(AreaTable.Cols.EXPLORED, explored);
        cv.put(AreaTable.Cols.DESCRIPTION, a.getDescription());
        String[] whereValue = {};
        db.update(AreaTable.NAME, cv,
                AreaTable.Cols.ID + " = " + a.getId(), whereValue);
    }

    public void removeArea(Area a)
    {
        String[] whereValue = {};
        db.delete(AreaTable.NAME,
                AreaTable.Cols.ID + " = " + a.getId(), whereValue);
    }

    public void addPlayer(Player p)
    {
        ContentValues cv = new ContentValues();

        cv.put(PlayerTable.Cols.ROWLOCATION, p.getRowLocation());
        cv.put(PlayerTable.Cols.COLLOCATION, p.getColLocation());
        cv.put(PlayerTable.Cols.CASH, p.getCash());
        cv.put(PlayerTable.Cols.HEALTH, p.getHealth());
        cv.put(PlayerTable.Cols.EQUIPMENTMASS, p.getEquipmentMass());
        db.insert(PlayerTable.NAME, null, cv);
    }

    public void udpatePlayer(Player p)
    {
        ContentValues cv = new ContentValues();

        cv.put(PlayerTable.Cols.ROWLOCATION, p.getRowLocation());
        cv.put(PlayerTable.Cols.COLLOCATION, p.getColLocation());
        cv.put(PlayerTable.Cols.CASH, p.getCash());
        cv.put(PlayerTable.Cols.HEALTH, p.getHealth());
        cv.put(PlayerTable.Cols.EQUIPMENTMASS, p.getEquipmentMass());
        String[] whereValue = {};
        db.update(PlayerTable.NAME, cv,
                PlayerTable.Cols.ID + " = " + p.getId(), whereValue);
    }

    public void removePlayer(Player p)
    {
        String[] whereValue = {};
        db.delete(PlayerTable.NAME,
                PlayerTable.Cols.ID + " = " + p.getId(), whereValue);
    }

    public void addItem(Item i, int rLoc, int cLoc, int store)
    {
        ContentValues cv = new ContentValues();

        int type;
        double special;
        String use;

        if (i instanceof Equipment)
        {
            type = 0;
            special = ((Equipment) i).getMass();
            use = ((Equipment) i).getUse();
        }
        else
        {
            type = 1;
            special = ((Food) i).getHealth();
            use = "none";
        }

        cv.put(ItemTable.Cols.NAME, i.getName());
        cv.put(ItemTable.Cols.DESCRIPTION, i.getDescription());
        cv.put(ItemTable.Cols.VALUE, i.getValue());
        cv.put(ItemTable.Cols.ROWLOCATION, rLoc);
        cv.put(ItemTable.Cols.COLLOCATION, cLoc);
        cv.put(ItemTable.Cols.STORED, store);
        cv.put(ItemTable.Cols.TYPE, type);
        cv.put(ItemTable.Cols.SPECIAL, special);
        cv.put(ItemTable.Cols.USE, use);
        db.insert(ItemTable.NAME, null, cv);
    }

    public void updateItem(Item i, int rLoc, int cLoc, int store)
    {
        ContentValues cv = new ContentValues();

        int type;
        double special;
        String use;

        if (i instanceof Equipment)
        {
            type = 0;
            special = ((Equipment) i).getMass();
            use = ((Equipment) i).getUse();
        }
        else
        {
            type = 1;
            special = ((Food) i).getHealth();
            use = "none";
        }

        cv.put(ItemTable.Cols.NAME, i.getName());
        cv.put(ItemTable.Cols.DESCRIPTION, i.getDescription());
        cv.put(ItemTable.Cols.VALUE, i.getValue());
        cv.put(ItemTable.Cols.ROWLOCATION, rLoc);
        cv.put(ItemTable.Cols.COLLOCATION, cLoc);
        cv.put(ItemTable.Cols.STORED, store);
        cv.put(ItemTable.Cols.TYPE, type);
        cv.put(ItemTable.Cols.SPECIAL, special);
        cv.put(ItemTable.Cols.USE, use);
        String[] whereValue = {};
        db.update(ItemTable.NAME, cv,
                ItemTable.Cols.ID + " = " + i.getId(), whereValue);
    }

    public void removeItem(Item i)
    {
        String[] whereValue = {};
        db.delete(ItemTable.NAME,
                ItemTable.Cols.ID + " = " + i.getId(), whereValue);
    }

    public GameCursor loadArea()
    {
        GameCursor cursor = new GameCursor(
                db.query(AreaTable.NAME, // FROM Area table
                        null, // SELECT all columns
                        null, // WHERE clause (null = all rows)
                        null, // WHERE arguments
                        null, // GROUP BY clause
                        null, // HAVING clause
                        null) // ORDER BY clause
        );

        return cursor;
    }

    public GameCursor loadPlayer()
    {
        GameCursor cursor = new GameCursor(
                db.query(PlayerTable.NAME, // FROM Area table
                        null, // SELECT all columns
                        null, // WHERE clause (null = all rows)
                        null, // WHERE arguments
                        null, // GROUP BY clause
                        null, // HAVING clause
                        null) // ORDER BY clause
        );

        return cursor;
    }

    public GameCursor loadItems()
    {
        GameCursor cursor = new GameCursor(
                db.query(ItemTable.NAME, // FROM Area table
                        null, // SELECT all columns
                        null, // WHERE clause (null = all rows)
                        null, // WHERE arguments
                        null, // GROUP BY clause
                        null, // HAVING clause
                        null) // ORDER BY clause
        );

        return cursor;
    }
}
