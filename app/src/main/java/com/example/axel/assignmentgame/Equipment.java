package com.example.axel.assignmentgame;

public class Equipment extends Item
{
    private String use;
    private double mass;

    public Equipment()
    {
        super();
        mass = 5.0;
        use = "none";
    }

    public Equipment(String inName, String indesc, int inValue, double inMass, String inUse)
    {
        super(inName, indesc, inValue);
        mass = inMass;
        use = inUse;
    }

    public Equipment(int id, String inName, String indesc, int inValue, double inMass, String inUse)
    {
        super(id, inName, indesc, inValue);
        mass = inMass;
        use = inUse;
    }

    public String getUse() { return use; }
    public double getMass() { return mass; }
}
