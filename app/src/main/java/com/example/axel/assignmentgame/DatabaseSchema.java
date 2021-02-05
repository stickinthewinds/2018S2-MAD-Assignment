package com.example.axel.assignmentgame;

public class DatabaseSchema
{
    public static class AreaTable
    {
        public static final String NAME = "areas"; // table name
        public static class Cols
        {
            // column names
            public static final String ID = "area_id";
            public static final String ROWLOCATION = "rowLocation";
            public static final String COLLOCATION = "colLocation";
            // Type, starred and explored are 0 for true 1 for false
            public static final String TYPE = "type";
            public static final String STARRED = "starred";
            public static final String EXPLORED = "explored";
            public static final String DESCRIPTION = "description";
        }
    }

    public static class ItemTable
    {
        public static final String NAME = "items"; // table name
        public static class Cols
        {
            // column names
            public static final String ID = "item_id";
            public static final String NAME = "name";
            public static final String DESCRIPTION = "description";
            public static final String VALUE = "value";
            public static final String ROWLOCATION = "rowLocation";
            public static final String COLLOCATION = "colLocation";
            public static final String STORED = "stored"; // 0 for area 1 for player
            public static final String TYPE = "type"; // 0 for equipment 1 for food
            public static final String SPECIAL = "special"; // mass/health
            public static final String USE = "use"; // "none" if food
        }
    }

    public static class PlayerTable
    {
        public static final String NAME = "players"; // table name
        public static class Cols
        {
            // column names
            public static final String ID = "player_id";
            public static final String ROWLOCATION = "rowLocation";
            public static final String COLLOCATION = "colLocation";
            public static final String CASH = "cash";
            public static final String HEALTH = "health";
            public static final String EQUIPMENTMASS = "equipmentMass";
        }
    }
}
