package com.example.axel.assignmentgame;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.axel.assignmentgame.DatabaseSchema.*;

public class GameCursor extends CursorWrapper
{
    public GameCursor(Cursor cursor) { super(cursor); }

    public void getArea(Area[][] grid)
    {
        boolean town = true, starred = true, explored = true;

        int id = getInt(getColumnIndex(AreaTable.Cols.ID));
        int rowLocation = getInt(getColumnIndex(AreaTable.Cols.ROWLOCATION));
        int colLocation = getInt(getColumnIndex(AreaTable.Cols.COLLOCATION));
        int type = getInt(
                getColumnIndex(AreaTable.Cols.TYPE));
        String desc = getString(
                getColumnIndex(AreaTable.Cols.DESCRIPTION));
        int star = getInt(
                getColumnIndex(AreaTable.Cols.STARRED));
        int explore = getInt(
                getColumnIndex(AreaTable.Cols.EXPLORED));

        if (type == 1)
            town = false;
        if (star == 1)
            starred = false;
        if (explore == 1)
            explored = false;

        Area loadArea = new Area(id, town, desc, starred, explored);

        grid[rowLocation][colLocation] = loadArea;
    }

    public Player getPlayer()
    {
        Player loadPlayer;

        int id = getInt(getColumnIndex(PlayerTable.Cols.ID));
        int rowLocation = getInt(getColumnIndex(PlayerTable.Cols.ROWLOCATION));
        int colLocation = getInt(getColumnIndex(PlayerTable.Cols.COLLOCATION));
        int cash = getInt(
                getColumnIndex(PlayerTable.Cols.CASH));
        double health = getDouble(
                getColumnIndex(PlayerTable.Cols.HEALTH));
        double equipMass = getInt(
                getColumnIndex(PlayerTable.Cols.EQUIPMENTMASS));
        loadPlayer =  new Player(id, cash, health, equipMass);
        loadPlayer.setRowLocation(rowLocation);
        loadPlayer.setColLocation(colLocation);

        return loadPlayer;
    }

    public void getItem(GameData gD)
    {
        Item returnItem;
        Area[][] grid = gD.getGrid();

        int id = getInt(getColumnIndex(ItemTable.Cols.ID));
        int rowLocation = getInt(getColumnIndex(ItemTable.Cols.ROWLOCATION));
        int colLocation = getInt(getColumnIndex(ItemTable.Cols.COLLOCATION));
        String name = getString(
                getColumnIndex(ItemTable.Cols.NAME));
        String desc = getString(
                getColumnIndex(ItemTable.Cols.DESCRIPTION));
        int value = getInt(
                getColumnIndex(ItemTable.Cols.VALUE));
        int stored = getInt(
                getColumnIndex(ItemTable.Cols.STORED));
        int type = getInt(
                getColumnIndex(ItemTable.Cols.TYPE));
        double special = getDouble(
                getColumnIndex(ItemTable.Cols.SPECIAL));
        String use = getString(
                getColumnIndex(ItemTable.Cols.DESCRIPTION));

        if (stored == 0 && type == 0)
            gD.getPlayer().getEquipment().add(new Equipment(id, name, desc, value, special, use));
        else if (stored == 1 && type == 0)
            grid[rowLocation][colLocation].getItems().add(new Equipment(id, name, desc, value, special, use));
        else if (stored == 1 && type == 1)
            grid[rowLocation][colLocation].getItems().add(new Food(id, name, desc, value, special));
    }
}
