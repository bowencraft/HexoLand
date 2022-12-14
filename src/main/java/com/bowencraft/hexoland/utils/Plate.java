package com.bowencraft.hexoland.utils;

import com.bowencraft.hexoland.Hexoland;

import java.util.List;

public class Plate {
    
    private static Hexoland plugin;
    
    public Plate(Hexoland plugin) {
        this.plugin = plugin;
    }
    
    private int offX;
    private int offZ;
    
    private Edge[] edges = new Edge[6];
    private String owner_uuid;
    private int level;
    private String type; // player, admin, resource
    
    private String typeSpace; // locked, core, extend,
    private boolean ShowStatus;
    private int height;
    private String biome; // resource-biomes, biomes, none
    private List<String> members;
    
    public Plate(int offX, int offZ, Edge[] edges, String owner_uuid, int level, String type, String typeSpace,
                 boolean showStatus, int height, String biome, List<String> members) {
        this.offX = offX;
        this.offZ = offZ;
        this.edges = edges;
        this.owner_uuid = owner_uuid;
        this.level = level;
        this.type = type;
        this.typeSpace = typeSpace;
        ShowStatus = showStatus;
        this.height = height;
        this.biome = biome;
        this.members = members;
    }
    
    public Plate(int ox, int oz, String owner, String type, String typeSpace, int height, String biome) {
        offX = ox;
        offZ = oz;
        owner_uuid = null; // Bukkit.getPlayer(owner).getUniqueId().toString();
        this.type = type;
        level = 0;
        this.height = height;
        this.biome = biome;
        ShowStatus = false;
        edges[0] = new Edge(false, ox, oz+1); // 0°
        edges[1] = new Edge(false, ox+1, oz); // 60°
        edges[2] = new Edge(false, ox, oz-1); // 120°
        edges[3] = new Edge(false, ox-1, oz-1); // 180°
        edges[4] = new Edge(false, ox-1, oz); // 240°
        edges[5] = new Edge(false, ox-1, oz+1); // 300°
        
    }
    
    public int getOffX() {
        return offX;
    }
    
    public int getOffZ() {
        return offZ;
    }
    
    public String getOwner_uuid() {
        return owner_uuid;
    }
    
    public void setOwner_uuid(String owner_uuid) {
        this.owner_uuid = owner_uuid;
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public boolean getShowStatus() {
        return ShowStatus;
    }
    
    public void setShowStatus(boolean showStatus) {
        ShowStatus = showStatus;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public String getBiome() {
        return biome;
    }
    
    public void setBiome(String biome) {
        this.biome = biome;
    }
    
    public String getTypespace() {
        return typeSpace;
    }
    
    public void setTypespace(String typespace) {
        this.typeSpace = typespace;
    }
    
    public Edge getEdge(int index) {
        if (index < edges.length) {
            return edges[index];
        } else {
            System.out.println("index of getEdge out of bound warning!");
            return null;
        }
    }
    
    public List<String> getMembers() {
        return members;
    }
}
