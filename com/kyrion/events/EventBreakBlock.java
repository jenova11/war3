package com.kyrion.events;

import com.kyrion.command.warCommand;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.engine.EngineMain;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Desc...
 * Created by Thog the 28/11/2015
 */
public class EventBreakBlock implements Listener
{
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void breakBlock(BlockBreakEvent event)
    {
        if (EngineMain.canPlayerBuildAt(event.getPlayer(), PS.valueOf(event.getBlock()), false)){
        	
        	event.setCancelled(false);
        	 return;
        }else{
        	
            MPlayer mplayer = MPlayer.get(event.getPlayer());
            Location location = event.getPlayer().getLocation();
            Faction faction = BoardColl.get().getFactionAt(PS.valueOf(location));
            if (faction != null)
            {
            	
            	
                Rel relation = mplayer.getFaction().getRelationWish(faction);
                if (!(event.getBlock().getType() == Material.CHEST)&&!(event.getBlock() .getType() == Material.TRAPPED_CHEST) && relation.equals(Rel.ENEMY) && warCommand.couldown.contains(faction.getName()))
                {
                	event.getBlock().getDrops().clear();
                    event.setCancelled(false);
                    return;
                }
            }
            event.setCancelled(true);
        }
            

    }
}