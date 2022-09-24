package com.bowencraft.hexoland.initialize;

import com.bowencraft.hexoland.Hexoland;
import com.bowencraft.hexoland.files.data;
import com.bowencraft.hexoland.utils.Edge;
import com.bowencraft.hexoland.utils.HexAlgs;
import com.bowencraft.hexoland.utils.Plate;
import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlateOperation {
    
    private static Hexoland plugin;
    
    public PlateOperation(Hexoland plugin) {
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
                String typeSpace = data.get().getString("Plate."+ key +".typeSpace");
                String biome = data.get().getString("Plate."+ key +".biome");
                
                boolean ShowStatus = data.get().getBoolean("Plate."+ key +".showStatus");
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
                Plate plate = new Plate(offX, offZ, edges, owner_uuid, level,type, typeSpace, ShowStatus, height, biome, members);
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
            data.get().set("Plate."+ offX + "," + offZ +".showStatus", plate.getShowStatus());
            data.get().set("Plate."+ offX + "," + offZ +".typeSpace", plate.getTypespace());
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
    
    // 0-5 represents anti-clockwise from 0°
    public static Plate[] getNearPlates(int offX, int offZ) {
        Plate[] plates = new Plate[6];
        try {
            
            plates[0] = plateList.get((offX)+","+(offZ+1));
            plates[1] = plateList.get((offX+1)+","+(offZ));
            plates[2] = plateList.get((offX)+","+(offZ-1));
            plates[3] = plateList.get((offX-1)+","+(offZ-1));
            plates[4] = plateList.get((offX-1)+","+(offZ));
            plates[5] = plateList.get((offX-1)+","+(offZ+1));
        
        } catch (NullPointerException e) {
            System.out.println("Plate does not exist.");
        }
        
        return plates;
    }
    
    // 批量创建plates
    public static void initialPlates(int minX, int maxX, int minZ, int maxZ) {
        if (minX<=maxX && minZ<=maxZ) {
            for (int i=minX; i<=maxX; i++) {
                for (int j=minZ; j<=maxZ; j++) {
                    if (!plateList.containsKey(i+","+j)) {
                        
                        // create plate schem
                        placePlateSchem(i, j, "unlocked.schem");
    
                        String type = "player";
                        int height = (int)Math.floor(Math.random()*5);
                        int random = (int) Math.floor(Math.random()*3);
                        String biome;
                        if (random == 0) {
                            biome = "plain";
                        } else if (random == 1) {
                            biome = "desert";
                        } else if (random == 2) {
                            biome = "mountain";
                            height += 3;
                        } else {
                            biome = "default";
                        }
                        String typeSpace = "locked";
                        Plate plate = new Plate(i,j,null,type,typeSpace,height,biome);
                        plateList.put(i+","+j,plate);
                        
                    }
                }
            }
        }
    }
    
    // 创建单体plate
    public static void newPlate(int offX, int offZ) {
        if (!plateList.containsKey(offX+","+offZ)) {
            // create plate schem
    
            placePlateSchem(offX, offZ, "unlocked.schem");
            
            String type = "player";
            int height = (int)Math.floor(Math.random()*5);
            int random = (int) Math.floor(Math.random()*3);
            String biome;
            if (random == 0) {
                biome = "plain";
            } else if (random == 1) {
                biome = "desert";
            } else if (random == 2) {
                biome = "mountain";
                height += 3;
            } else {
                biome = "default";
            }
            String typeSpace = "locked";
            Plate plate = new Plate(offX,offZ,null,type,typeSpace,height,biome);
            plateList.put(offX+","+offZ,plate);
        }
    }
    
    // 为玩家解锁plate
    public static void unlockPlate(int offX, int offZ, String owner_uuid, String typeSpace) {
        if (plateList.containsKey(offX+","+offZ)) {
            Plate plate = plateList.get(offX+","+offZ);
            if (plate.getOwner_uuid()==null) {
                if (plate.getType()=="player") {
                    if (typeSpace == "extend") {
                        Plate[] plates = getNearPlates(offX,offZ);
                        if (
                            plates[0].getOwner_uuid().equals(owner_uuid) ||
                            plates[1].getOwner_uuid().equals(owner_uuid) ||
                            plates[2].getOwner_uuid().equals(owner_uuid) ||
                            plates[3].getOwner_uuid().equals(owner_uuid) ||
                            plates[4].getOwner_uuid().equals(owner_uuid) ||
                            plates[5].getOwner_uuid().equals(owner_uuid)
                        ) {
                            placePlateSchem(offX, offZ, plate.getBiome()+".schem");

                            plate.setShowStatus(true);
                            plate.setTypespace("extend");
                            plate.setOwner_uuid(owner_uuid);

                            plateList.put(offX+","+offZ,plate);
                            
                        }
                    }
                    else if (typeSpace == "core") {
                        
                        placePlateSchem(offX, offZ, plate.getBiome()+".schem");
    
                        plate.setShowStatus(true);
                        plate.setTypespace("core");
                        plate.setOwner_uuid(owner_uuid);
    
                        plateList.put(offX+","+offZ,plate);
                    
                    }
                    else {
                        System.out.println("Invalid input. Plase try again.");
                    }
                    plate.setTypespace(typeSpace);
                    plate.setOwner_uuid(owner_uuid);
                } else {
                    System.out.println("This plate is not available for the player to unlock.");
                }
            } else {
                System.out.println("Plate " + offX + "," + offZ + " already been unlocked and owned by others.");
            }
        } else {
            System.out.println("This plate does not exist.");
        }
    }
    
    public static void placePlateSchem(int offX, int offZ, String schem) {
        int[] coord = HexAlgs.offsetToCentralXY(offX,offZ);
        Plate plate = plateList.get(offX+","+offZ);
    
        File file = new File(plugin.getDataFolder().getPath(), "/schem/" + schem);
    
        int x = coord[0];
        int y = plugin.getConfig().getInt("sea-level") + plate.getHeight();
        int z = coord[1];
    
        Clipboard clipboard;
    
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            clipboard = reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    
        World world = FaweAPI.getWorld(plugin.getConfig().getString("world"));
    
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
            Operation operation = new ClipboardHolder(clipboard)
                .createPaste(editSession)
                .to(BlockVector3.at(x, y, z))
                .ignoreAirBlocks(true)
                .build();
            Operations.complete(operation);
        }
        
    }
    
}
