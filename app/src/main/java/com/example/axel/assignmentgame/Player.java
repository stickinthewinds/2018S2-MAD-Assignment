package com.example.axel.assignmentgame;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Player implements Serializable
{
    private final int id;

    private int rowLocation;
    private int colLocation;
    private int cash;
    private double health;
    private double equipmentMass;
    private List<Equipment> equipment;

    private static int nextId = 0;

    public Player()
    {
        id = nextId;
        cash = 15;
        health = 100.0;
        equipmentMass = 0.0;
        equipment = new LinkedList<>();
        nextId++;
    }

    public Player(int id, int inCash, double inHealth, double inEquipmentMass)
    {
        this.id = id;
        cash = inCash;
        health = inHealth;
        equipmentMass = inEquipmentMass;
        equipment = new LinkedList<>();
        nextId = id + 1;
    }

    public Player(int inCash, double inHealth, double inEquipmentMass)
    {
        this(nextId, inCash, inHealth, inEquipmentMass); // Delegate to the other constructor
        nextId++;
    }

    public int getId() { return id; }
    public int getRowLocation() { return rowLocation; }
    public int getColLocation() { return colLocation; }
    public int getCash() { return cash; }
    public double getHealth() { return health; }
    public double getEquipmentMass() { return equipmentMass; }
    public List<Equipment> getEquipment() { return equipment; }

    public void setCash(int inCash)
    {
        cash = inCash;
    }
    public void setHealth(double inHealth)
    {
        health = inHealth;
    }

    public void setEquipmentMass(double inEquipmentMass) { equipmentMass = inEquipmentMass; }

    public void setRowLocation(int inLocation)
    {
        rowLocation = inLocation;
    }

    public void setColLocation(int inLocation)
    {
        colLocation = inLocation;
    }

    public void addEquipment(Equipment inEquipment)
    {
        equipment.add(inEquipment);
        equipmentMass += inEquipment.getMass();
    }

    public void sellEquipment(Equipment outEquipment)
    {
        if (!equipment.isEmpty())
        {
            equipment.remove(outEquipment);
            equipmentMass -= outEquipment.getMass();
            cash += outEquipment.getValue();
        }
    }

    @Override
    public String toString() {
        return "Cash: $" + cash
                + " Health: " + health
                + " Equipment Mass: " + equipmentMass;
    }

    public void movePlayer(char direction) {
        health = Math.max(0.0, health - 5.0 - (equipmentMass / 2));
        switch (direction)
        {
            case 'n':
                colLocation--;
                break;
            case 's':
                colLocation++;
                break;
            case 'e':
                rowLocation++;
                break;
            case 'w':
                rowLocation--;
                break;
        }
    }
}
