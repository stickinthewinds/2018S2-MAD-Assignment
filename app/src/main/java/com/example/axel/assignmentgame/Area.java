package com.example.axel.assignmentgame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Area implements Serializable
{
    private final int id;

    private boolean town;
    private List<Item> items;
	private String description;
	private boolean starred;
	private boolean explored;

    private static int nextId = 0;

    public Area()
    {
        id = nextId;
        town = true;
        description = "";
        starred = false;
        explored = false;
        items = new ArrayList<>();
        nextId++;
    }

    //public Area(String inName, boolean inTown, List<Item> inItems)
    public Area(int id, boolean inTown, String inDesc, boolean inStarred, boolean inExplored)
    {
        this.id = id;
        town = inTown;
        items = new ArrayList<>();
        description = inDesc;
        starred = inStarred;
        explored = inExplored;
        nextId = id + 1;
    }

    public Area(boolean inTown, String inDesc, boolean inStarred, boolean inExplored)
    {
        this(nextId, inTown, inDesc, inStarred, inExplored); // Delegate to the other constructor
        nextId++;
    }

    public int getId() { return id; }
    public boolean isTown() { return town; }
    public String getDescription() { return description; }
    public boolean isStarred() { return starred; }
    public boolean isExplored() { return explored; }
    public List<Item> getItems() { return items; }

    public void setTown(boolean inTown) { town = inTown; }

    public void setItems(List<Item> inItems) { items = inItems; }

    public void setDescription(String inDescription) { description = inDescription; }

    public void setStarred(boolean inStarred)
    {
        starred = inStarred;
    }

    public void setExplored(boolean inExplored)
    {
        explored = inExplored;
    }
}
