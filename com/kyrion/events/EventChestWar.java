package com.kyrion.events;




import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.kyrion.command.warCommand;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.massivecore.ps.PS;


public class EventChestWar implements Listener{
	
	public ConsoleCommandSender console;
	
	@SuppressWarnings("deprecation")
	@EventHandler (priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent e){
		
		
		console = Bukkit.getServer().getConsoleSender();
		Player p = e.getPlayer();
		MPlayer mplayer = MPlayer.get(p);
		Location location = e.getPlayer().getLocation();
		
		Faction faction = BoardColl.get().getFactionAt(PS.valueOf(location));
		Rel relation = mplayer.getFaction().getRelationWish(faction);
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
				
				if(e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAPPED_CHEST){
					
					if(relation == Rel.ENEMY){
						
						if(warCommand.couldown.contains(faction.getName())){
							if(!warCommand.CoffreOpen.get(mplayer.getFaction().getName()).contains(e.getClickedBlock().getX()+"|"+e.getClickedBlock().getY()+"|"+e.getClickedBlock().getZ())){
								if(p.getItemInHand() != null && p.getItemInHand().getTypeId() == 4374){
									

								     
								    
									Chest ch = (Chest) e.getClickedBlock().getState();
									
					
									ItemStack[] item =  ch.getInventory().getContents();
					
									ArrayList<Integer> Contenue = new ArrayList<Integer>();
									ArrayList<Integer> Adrop = new ArrayList<Integer>();
									Integer i = 0;
					
									Location l = ch.getLocation();
					
									for(ItemStack items : item){
										if(items != null){
											Contenue.add(i);
										}
										i++;
									}
									 
									 //if (intemName)
										
									Integer pourcent = 0;
									String intemName = p.getItemInHand().getItemMeta().getDisplayName();
								     //System.out.println(intemName);
								     if(p.getItemInHand().hasItemMeta()){
								    	 
								    
								    	 if(intemName.equals("§7Pied de Biche")){//gris
								    	 	pourcent = Contenue.size()*3/100;

								     	}else if(intemName.equals("§aPied de Biche")){//
								    	 	pourcent = Contenue.size()*10/100;

								     	}else if(intemName.equals("§5Pied de Biche")){//
								    	 	pourcent = Contenue.size()*15/100;

								     	}
								     }else{
								    	 pourcent = Contenue.size()*7/100; 
								     }
								     short Durability = p.getInventory().getItemInHand().getDurability();
								     
								     
								     Durability = (short) (Durability + 50);
								     
								    
								     p.getInventory().getItemInHand().setDurability(Durability);
								     
								     if(Durability >= 250){
								    	 p.getInventory().setItemInHand(null);
								     }
								    
									Collections.shuffle(Contenue);
									i = 0;
									for(Integer position : Contenue){
										Adrop.add(position);
										if(i == pourcent){
											break;
										}else{
											i++;
										}
									}
									i = 0;
									for(ItemStack items : item){
										if(Adrop.contains(i)){
											//System.out.println("Drop => position:"+i+" Item:"+items.getType()+"x"+items.getAmount());
											l.getWorld().dropItemNaturally(l, items);
											//ch.getInventory().remove(items);
										
											ch.getInventory().removeItem(items);
										}
										i++;
									}
					
									warCommand.CoffreOpen.get(mplayer.getFaction().getName()).add(e.getClickedBlock().getX()+"|"+e.getClickedBlock().getY()+"|"+e.getClickedBlock().getZ());
					
								//p.sendMessage(ch.getInventory().getContents().toString());
								}
							}else{
								p.sendMessage("Se coffre a déja était ouvert!");
							}
							e.setCancelled(true);
						}
					}
				}
			}
		
	}
}
