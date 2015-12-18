package com.kyrion.command;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;

public class setCrystalCommand implements CommandExecutor {
	
	MPlayer mplayer;
	FileConfiguration spawnCrystalConfig;
	File spawnCrystalFile;
	Faction faction;
	
	public setCrystalCommand(FileConfiguration spawnCrystal, File PathToConfig) {
		// TODO Auto-generated constructor stub
		spawnCrystalConfig = spawnCrystal;
		spawnCrystalFile = PathToConfig;
	}

	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if(sender instanceof Player) {
             Player p = (Player)sender;
             mplayer = MPlayer.get(p);
             
             if(mplayer.hasFaction() != false){
            	faction = mplayer.getFaction(); 
             	if(!spawnCrystalConfig.isSet(faction.getName())){
             		if(mplayer.isInOwnTerritory()){
             			Location location = p.getLocation();
             			
             			spawnCrystalConfig.set(faction.getName(), 1);
             			spawnCrystalConfig.set(faction.getName()+".x", location.getBlockX());
             			spawnCrystalConfig.set(faction.getName()+".y", location.getBlockY());
             			spawnCrystalConfig.set(faction.getName()+".z", location.getBlockZ());
             			try {
							spawnCrystalConfig.save(spawnCrystalFile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
             			
             			p.sendMessage("Le point de spawn du crystal a biene tait définie.");
             		}else{
             			p.sendMessage("Vous devez etre dans vos claim pour définir le point de spawn du crystal.");
             		}
             	}else{
             		p.sendMessage("Le spawn du crystal est deja défini.");
             	}
             }else{
            	 p.sendMessage("Vous devez etre dans une faction pour set le spawn");
             }
             
             return true;
    	}
		return false;
    }

}