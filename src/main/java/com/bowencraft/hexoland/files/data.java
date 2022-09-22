package com.bowencraft.hexoland.files;

import com.bowencraft.hexoland.initialize.initPlate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class data {
    private static File file;
    private static FileConfiguration hexodata;
    public static FileConfiguration get() {
        return hexodata;
    }
    
    // finds or generates the custom config file
    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Hexoland").getDataFolder(), "/data/data.yml");
        if (!file.exists()){
            try{
                file.createNewFile();
            } catch(IOException e){}
            
        }
        hexodata = YamlConfiguration.loadConfiguration(file);
        // hexodata.set("data-type.offsettopolar", 6);
    
        initPlate.loadPlate();
    }
    
    public static void save() {
        try{
            hexodata.save(file);
            
        } catch(IOException e){
            System.out.println("Couldn't save file");
        }
        
        initPlate.savePlate();
    }
    
    public static void reload(){
        hexodata = YamlConfiguration.loadConfiguration(file);
    }
}
