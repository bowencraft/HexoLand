package com.bowencraft.hexoland.utils;

import com.bowencraft.hexoland.Hexoland;

public class Edge {
    private static Hexoland plugin;
    
    public Edge(Hexoland plugin) {
        this.plugin = plugin;
    }
    private boolean WarStatus;
    private int nearPlateX;
    private int nearPlateZ;
    
    public Edge(boolean warSt, int platex, int platez) {
        WarStatus = warSt;
        nearPlateX = platex;
        nearPlateZ = platez;
    }
    
    public boolean isWarStatus() {
        return WarStatus;
    }
    
    public void setWarStatus(boolean warStatus) {
        WarStatus = warStatus;
    }
    
    public int getNearPlateX() {
        return nearPlateX;
    }
    
    public void setNearPlateX(int nearPlateX) {
        this.nearPlateX = nearPlateX;
    }
    
    public int getNearPlateZ() {
        return nearPlateZ;
    }
    
    public void setNearPlateZ(int nearPlateZ) {
        this.nearPlateZ = nearPlateZ;
    }
}
