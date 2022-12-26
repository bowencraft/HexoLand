package com.bowencraft.hexoland;

import com.bowencraft.hexoland.files.data;
import com.bowencraft.hexoland.initialize.PlateOperation;
import com.bowencraft.hexoland.utils.HexAlgs;
import com.bowencraft.hexoland.utils.Plate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
        new PlateOperation(this);
        
        data.setup();
        data.get().options().copyDefaults(true);
        data.save();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        System.out.println("command executed");
        if (command.getName().equalsIgnoreCase("hexoland")) {
            if (args[0].equalsIgnoreCase("create")) {
    
                PlateOperation.initialPlates(Integer.parseInt(args[1]),Integer.parseInt((args[2])),Integer.parseInt(args[3]),Integer.parseInt(args[4]));
                
            } else if (args[0].equalsIgnoreCase("unlock")) {
    
                PlateOperation.unlockPlate(Integer.parseInt(args[1]),Integer.parseInt((args[2])),getServer().getPlayer(args[3]).getUniqueId().toString(),args[4]);
                
            } else if (args[0].equalsIgnoreCase("save")) {
    
                PlateOperation.savePlate();
            } else if (args[0].equalsIgnoreCase("unlocks")) {
    
                PlateOperation.unlockPlateScope(Integer.parseInt(args[1]),Integer.parseInt(args[2]),
                    Integer.parseInt(args[3]),Integer.parseInt(args[4]),getServer().getPlayer(args[5]).getUniqueId().toString(),args[6]);
            }
        }
        return true;
    }
    @Override
    public void onDisable() {
        saveDefaultConfig();
        data.save();
        // Plugin shutdown logic
    }
}
