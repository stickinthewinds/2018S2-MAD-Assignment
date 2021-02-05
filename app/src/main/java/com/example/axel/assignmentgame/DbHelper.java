package com.example.axel.assignmentgame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.axel.assignmentgame.DatabaseSchema.*;

public class DbHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "game.db";

    public DbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db)
    {
        // Table for areas
        db.execSQL("create table " + AreaTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                AreaTable.Cols.ID + ", " +
                AreaTable.Cols.ROWLOCATION + ", " +
                AreaTable.Cols.COLLOCATION + ", " +
                AreaTable.Cols.TYPE + ", " +
                AreaTable.Cols.STARRED + ", " +
                AreaTable.Cols.EXPLORED + ", " +
                AreaTable.Cols.DESCRIPTION + ")");

        // Tables for items with isA for Equipment and Food
        db.execSQL("create table " + ItemTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ItemTable.Cols.ID + ", " +
                ItemTable.Cols.NAME + ", " +
                ItemTable.Cols.DESCRIPTION + ", " +
                ItemTable.Cols.VALUE + ", " +
                ItemTable.Cols.ROWLOCATION + ", " +
                ItemTable.Cols.COLLOCATION + ", " +
                ItemTable.Cols.STORED + ", " +
                ItemTable.Cols.SPECIAL + ", " +
                ItemTable.Cols.USE + ")");

        // Table for the player character
        db.execSQL("create table " + PlayerTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                PlayerTable.Cols.ID + ", " +
                PlayerTable.Cols.ROWLOCATION + ", " +
                PlayerTable.Cols.COLLOCATION + ", " +
                PlayerTable.Cols.CASH + ", " +
                PlayerTable.Cols.HEALTH + ", " +
                PlayerTable.Cols.EQUIPMENTMASS + ")");
    }

    @Override public void onUpgrade(SQLiteDatabase db, int v1, int v2)
    {

    }
}
