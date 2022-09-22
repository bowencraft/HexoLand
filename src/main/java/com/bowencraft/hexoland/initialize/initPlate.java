package com.bowencraft.hexoland.initialize;

import com.bowencraft.hexoland.Hexoland;
import com.bowencraft.hexoland.files.data;
import com.bowencraft.hexoland.utils.Edge;
import com.bowencraft.hexoland.utils.Plate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class initPlate {
    
    private static Hexoland plugin;
    
    public initPlate(Hexoland plugin) {
        this.plugin = plugin;
    }
    private static final HashMap<String, Plate> plateList = new HashMap<>();
    
    // load
    public static void loadPlate() {
        try {
            for(String key : data.get().getConfigurationSection("Plate").getKeys(false))
            {
                String[] loc = key.split(",");
                int offX = Integer.parseInt(loc[0]);
                int offZ = Integer.parseInt(loc[1]);
                
                String owner_uuid = data.get().getString("Plate."+ key +".owner_uuid");
                String type = data.get().getString("Plate."+ key +".type");
                String biome = data.get().getString("Plate."+ key +".biome");
                
                boolean ShowStatus = data.get().getBoolean("Plate."+ key +".ShowStatus");
                List<String> members = null;
                for (int i=0; i<data.get().getList("Plate."+ key +".members").size(); i++) {
                    members.add(data.get().getList("Plate."+ key +".members").get(i).toString());
                }
    
                Edge[] edges = new Edge[6];
                for (int i=0; i<6; i++) {
                    Boolean WarStatus = data.get().getBoolean("Plate."+ key +".edges." + i + ".WarStatus");
                    int nearPlateX = data.get().getInt("Plate."+ key +".edges." + i + ".nearPlateX");
                    int nearPlateZ = data.get().getInt("Plate."+ key +".edges." + i + ".nearPlateZ");
                    edges[i] = new Edge(WarStatus, nearPlateX, nearPlateZ);
                }
                
                int level = data.get().getInt("Plate."+ key +".level");
                int height = data.get().getInt("Plate."+ key +".height");
                Plate plate = new Plate(offX, offZ, edges, owner_uuid, level,type,ShowStatus, height, biome, members);
                plateList.put(key, plate);
            
            }
        } catch (Exception e) {
            System.out.println("Plate data is missing or incomplete in data.yml.");
        }
    }
    
    public static void savePlate() {
        for(Map.Entry<String, Plate> entry : plateList.entrySet()) {
            
            Plate plate = entry.getValue();
            int offX = plate.getOffX();
            int offZ = plate.getOffZ();
            
            data.get().set("Plate."+ offX + "," + offZ +".owner_uuid", plate.getOwner_uuid());
            data.get().set("Plate."+ offX + "," + offZ +".level", plate.getLevel());
            data.get().set("Plate."+ offX + "," + offZ +".type", plate.getType());
            data.get().set("Plate."+ offX + "," + offZ +".ShowStatus", plate.getShowStatus());
            data.get().set("Plate."+ offX + "," + offZ +".height", plate.getHeight());
            data.get().set("Plate."+ offX + "," + offZ +".biome", plate.getBiome());
            data.get().set("Plate."+ offX + "," + offZ +".members", plate.getMembers());
            for (int i=0;i<6;i++) {
                data.get().set("Plate."+ offX + "," + offZ +".edges." + i + ".WarStatus", plate.getEdge(i).isWarStatus());
                data.get().set("Plate."+ offX + "," + offZ +".edges." + i + ".nearPlateX", plate.getEdge(i).getNearPlateX());
                data.get().set("Plate."+ offX + "," + offZ +".edges." + i + ".nearPlateZ", plate.getEdge(i).getNearPlateZ());
            }
        }
        
    }
}
