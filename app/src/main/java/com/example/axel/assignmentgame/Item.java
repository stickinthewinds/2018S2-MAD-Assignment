package com.example.axel.assignmentgame;

import java.io.Serializable;

public abstract class Item implements Serializable
{
    private final int id;

    private String name;
    private String description;
    private int value;

    private static int nextId = 0;

    public Item()
    {
        id = nextId;
        name = "A dog";
        description = "Very cute dog.";
        value = 50;
        nextId++;
    }

    public Item(int id, String inName, String inDesc, int inValue)
    {
        this.id = id;
        name = inName;
        description = inDesc;
        value = inValue;
        nextId = id + 1;
    }

    public Item(String inName, String inDesc, int inValue)
    {
        this(nextId, inName, inDesc, inValue); // Delegate to the other constructor
        nextId++;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getValue() { return value; }
}
