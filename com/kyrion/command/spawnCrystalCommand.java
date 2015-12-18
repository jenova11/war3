package com.kyrion.command;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class spawnCrystalCommand implements CommandExecutor
{

    MPlayer mplayer;
    FileConfiguration spawnCrystalConfig;
    File spawnCrystalFile;
    Faction faction;
    double x;
    double y;
    double z;
    static spawnCrystalCommand instance;
    Map<String, Entity> crystals = new HashMap<>();

    public spawnCrystalCommand(FileConfiguration spawnCrystal, File crystalConfig)
    {
        instance = this;
        spawnCrystalConfig = spawnCrystal;
        spawnCrystalFile = crystalConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        //On fait quelque chose
        if (sender instanceof Player || sender instanceof ConsoleCommandSender)
        {
            if (args.length == 2)
            {
                faction = FactionColl.get().getByName(args[0]);
                if (faction != null)
                {
                    if (spawnCrystalConfig.get(args[0]) != null)
                    {
                        x = spawnCrystalConfig.getDouble(args[0] + ".x");
                        y = spawnCrystalConfig.getDouble(args[0] + ".y");
                        z = spawnCrystalConfig.getDouble(args[0] + ".z");
                       
                    } else{
                        x = faction.getHome().getLocationX();
                        y = faction.getHome().getLocationY();
                        z = faction.getHome().getLocationZ();
                    }
                    World world = Bukkit.getServer().getWorld("kyrion");
                    Location loc = new Location(world, x, y, z);
                    Chunk chunk = loc.getChunk();
                    chunk.load(true);
                    
                    LivingEntity entity = (LivingEntity) world.spawnEntity(loc, EntityType.fromId(71));
                    entity.setRemoveWhenFarAway(false);
                    System.out.println(entity);
                    System.out.println(entity.isValid());
                    
                    crystals.put("Faction-" + args[0] + "-vs-" + args[1], entity);
                    entity.setCustomName("Faction-" + args[0] + "-vs-" + args[1]);
                } else
                {
                    return false;
                }
            } else
            {
                return false;
            }
            return true;
        }
        return false;
    }

}