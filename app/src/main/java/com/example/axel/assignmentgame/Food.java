package com.example.axel.assignmentgame;

public class Food extends Item
{
    private double health;

    public Food()
    {
        super("Pudding", "Yummy pudding", 7);
        health = 25.0;
    }

    public Food(String inName, String inDesc, int inValue, double inHealth)
    {
        super(inName, inDesc, inValue);
        health = inHealth;
    }

    public Food(int id, String inName, String inDesc, int inValue, double inHealth)
    {
        super(id, inName, inDesc, inValue);
        health = inHealth;
    }

    public double getHealth() { return health; }
}
