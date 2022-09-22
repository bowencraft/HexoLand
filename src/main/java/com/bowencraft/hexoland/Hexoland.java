package com.bowencraft.hexoland;

import com.bowencraft.hexoland.files.data;
import com.bowencraft.hexoland.utils.HexAlgs;
import com.bowencraft.hexoland.utils.Plate;
import org.bukkit.plugin.java.JavaPlugin;

public final class Hexoland extends JavaPlugin {
    
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("§aHexoland loaded successfully, version:" + getServer().getVersion());
        System.out.println("§6Hexoland §71.0.0-BETA  §7Author: Bo_Wen");
        System.out.println("§7Visit §f§nhexoland.bowen-craft.com §r§7for more information.");
    
        getConfig().options().copyDefaults();
        saveDefaultConfig();
    
        new HexAlgs(this);
        new Plate(this);
    
        data.setup();
        data.get().options().copyDefaults(true);
        data.save();
    }
    
    @Override
    public void onDisable() {
        saveDefaultConfig();
        data.save();
        // Plugin shutdown logic
    }
}
